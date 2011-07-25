/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *******************************************************************************/
/* This file has been modified by Open Source Strategies, Inc. */
package org.opentaps.entity.util.sequence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.sql.DataSource;

import org.opentaps.core.service.ServiceUtil;
import org.opentaps.entity.util.EntityUtilActivator;

/**
 * Sequence Utility to get unique sequences from named sequence banks
 * Uses a collision detection approach to safely get unique sequenced ids in banks from the database
 */
public class SequenceUtil {

    private DataSource dataSource = null;
    private final Map<String, SequenceBank> sequences = new Hashtable<String, SequenceBank>();
    private final long bankSize = SequenceBank.defaultBankSize;
    private final String tableName = "SEQUENCE_VALUE_ITEM";
    private final String nameColName = "seq_name";
    private final String idColName = "seq_id";

    public SequenceUtil() {
    }

    public Long getNextSeqId(String seqName, long staggerMax) {
        SequenceBank bank = this.getBank(seqName);
        return bank.getNextSeqId(staggerMax);
    }

    public void forceBankRefresh(String seqName, long staggerMax) {
        // don't use the get method because we don't want to create if it fails
        SequenceBank bank = sequences.get(seqName);
        if (bank == null) {
            return;
        }

        bank.refresh(staggerMax);
    }

    private SequenceBank getBank(String seqName) {
        SequenceBank bank = sequences.get(seqName);

        if (bank == null) {
            synchronized(this) {
                bank = sequences.get(seqName);
                if (bank == null) {
                    bank = new SequenceBank(seqName);
                    sequences.put(seqName, bank);
                }
            }
        }

        return bank;
    }

    private class SequenceBank {
        public static final long defaultBankSize = 10;
        public static final long maxBankSize = 5000;
        public static final long startSeqId = 10000;
        public static final long minWaitMillis = 5;
        public static final long maxWaitMillis = 50;
        public static final int maxTries = 5;

        private long curSeqId;
        private long maxSeqId;
        private final String seqName;

        private SequenceBank(String seqName) {
            this.seqName = seqName;
            curSeqId = 0;
            maxSeqId = 0;
            fillBank(1);
        }

        private synchronized Long getNextSeqId(long staggerMax) {
            long stagger = 1;
            if (staggerMax > 1) {
                stagger = Math.round(Math.random() * staggerMax);
                if (stagger == 0) stagger = 1;
            }

            if ((curSeqId + stagger) <= maxSeqId) {
                Long retSeqId = Long.valueOf(curSeqId);
                curSeqId += stagger;
                return retSeqId;
            } else {
                fillBank(stagger);
                if ((curSeqId + stagger) <= maxSeqId) {
                    Long retSeqId = Long.valueOf(curSeqId);
                    curSeqId += stagger;
                    return retSeqId;
                } else {
                    EntityUtilActivator.getInstance().logError("[SequenceUtil.SequenceBank.getNextSeqId] Fill bank failed, returning null", null);
                    return null;
                }
            }
        }

        private void refresh(long staggerMax) {
            this.curSeqId = this.maxSeqId;
            this.fillBank(staggerMax);
        }

        private synchronized void fillBank(long stagger) {
            // no need to get a new bank, SeqIds available
            if ((curSeqId + stagger) <= maxSeqId) return;

            long bankSize = SequenceUtil.this.bankSize;
            if (stagger > 1) {
                // NOTE: could use staggerMax for this, but if that is done it would be easier to guess a valid next id without a brute force attack
                bankSize = stagger * defaultBankSize;
            }

            if (bankSize > maxBankSize) bankSize = maxBankSize;

            long val1 = 0;
            long val2 = 0;

            // NOTE: the fancy ethernet type stuff is for the case where transactions not available, or something funny happens with really sensitive timing (between first select and update, for example)
            int numTries = 0;

            while (val1 + bankSize != val2) {
                EntityUtilActivator.getInstance().logInfo("[SequenceUtil.SequenceBank.fillBank] Trying to get a bank of sequenced ids for " +
                        this.seqName + "; start of loop val1=" + val1 + ", val2=" + val2 + ", bankSize=" + bankSize);

                // not sure if this synchronized block is totally necessary, the method is synchronized but it does do a wait/sleep
                //outside of this block, and this is the really sensitive block, so making sure it is isolated; there is some overhead
                //to this, but very bad things can happen if we try to do too many of these at once for a single sequencer
                synchronized (this) {
                    EntityManagerFactory emf = (EntityManagerFactory) ServiceUtil.getService(EntityManagerFactory.class.getName(), "(osgi.unit.name=default)");
                    EntityManager em = emf.createEntityManager();

                    EntityTransaction tx = em.getTransaction();

                    try {
                        tx.begin();

                        Connection connection = null;
                        Statement stmt = null;
                        ResultSet rs = null;

                        try {
                            connection = getConnection();
                        } catch (SQLException sqle) {
                            EntityUtilActivator.getInstance().logWarning("[SequenceUtil.SequenceBank.fillBank]: Unable to esablish a connection with the database... Error was:" + sqle.toString(), null, null);
                            throw sqle;
                        } catch (Exception e) {
                            EntityUtilActivator.getInstance().logWarning("[SequenceUtil.SequenceBank.fillBank]: Unable to esablish a connection with the database... Error was: " + e.toString(), null, null);
                            throw e;
                        }

                        if (connection == null) {
                            throw new Exception("[SequenceUtil.SequenceBank.fillBank]: Unable to esablish a connection with the database, connection was null...");
                        }

                        String sql = null;

                        try {
                            // we shouldn't need this, and some TX managers complain about it, so not including it: connection.setAutoCommit(false);

                            stmt = connection.createStatement();

                            sql = String.format("SELECT %1$s FROM %2$s WHERE %3$s='%4$s'", SequenceUtil.this.idColName, SequenceUtil.this.tableName, SequenceUtil.this.nameColName, this.seqName);
                            rs = stmt.executeQuery(sql);
                            boolean gotVal1 = false;
                            if (rs.next()) {
                                val1 = rs.getLong(SequenceUtil.this.idColName);
                                gotVal1 = true;
                            }
                            rs.close();

                            if (!gotVal1) {
                                EntityUtilActivator.getInstance().logWarning("[SequenceUtil.SequenceBank.fillBank] first select failed: will try to add new row, result set was empty for sequence [" + seqName + "] \nUsed SQL: " + sql + " \n Thread Name is: " + Thread.currentThread().getName() + ":" + Thread.currentThread().toString(), null, null);
                                sql = String.format("INSERT INTO %1$s (%2$s, %3$s) VALUES ('%4$s', %5$d)", SequenceUtil.this.tableName, SequenceUtil.this.nameColName, SequenceUtil.this.idColName, this.seqName, startSeqId);
                                if (stmt.executeUpdate(sql) <= 0) {
                                    throw new Exception("No rows changed when trying insert new sequence row with this SQL: " + sql);
                                }
                                continue;
                            }

                            sql = "UPDATE " + SequenceUtil.this.tableName + " SET " + SequenceUtil.this.idColName + "=" + SequenceUtil.this.idColName + "+" + bankSize + " WHERE " + SequenceUtil.this.nameColName + "='" + this.seqName + "'";
                            if (stmt.executeUpdate(sql) <= 0) {
                                throw new Exception("[SequenceUtil.SequenceBank.fillBank] update failed, no rows changes for seqName: " + seqName);
                            }

                            sql = "SELECT " + SequenceUtil.this.idColName + " FROM " + SequenceUtil.this.tableName + " WHERE " + SequenceUtil.this.nameColName + "='" + this.seqName + "'";
                            rs = stmt.executeQuery(sql);
                            boolean gotVal2 = false;
                            if (rs.next()) {
                                val2 = rs.getLong(SequenceUtil.this.idColName);
                                gotVal2 = true;
                            }

                            rs.close();

                            if (!gotVal2) {
                                throw new Exception("[SequenceUtil.SequenceBank.fillBank] second select failed: aborting, result set was empty for sequence: " + seqName);
                            }

                            // got val1 and val2 at this point, if we don't have the right difference between them, force a rollback (with
                            //setRollbackOnly and NOT with an exception because we don't want to break from the loop, just err out and
                            //continue), then flow out to allow the wait and loop thing to happen
                            if (val1 + bankSize != val2) {
                                EntityUtilActivator.getInstance().logWarning("Forcing transaction rollback in sequence increment because we didn't get a clean update, ie a conflict was found, so not saving the results", null, null);
                                tx.rollback();
                            }
                        } catch (SQLException sqle) {
                            EntityUtilActivator.getInstance().logWarning("[SequenceUtil.SequenceBank.fillBank] SQL Exception while executing the following:\n" + sql + "\nError was:" + sqle.getMessage(), sqle, null);
                            throw sqle;
                        } finally {
                            try {
                                if (stmt != null) stmt.close();
                            } catch (SQLException sqle) {
                                EntityUtilActivator.getInstance().logWarning("Error closing statement in sequence util", sqle, null);
                            }
                            try {
                                if (connection != null) connection.close();
                            } catch (SQLException sqle) {
                                EntityUtilActivator.getInstance().logWarning("Error closing connection in sequence util", sqle, null);
                            }
                        }
                    } catch (Exception e) {
                        String errMsg = "General error in getting a sequenced ID";
                        EntityUtilActivator.getInstance().logError(errMsg, e);
                        tx.rollback();
                        break;
                    } finally {
                        tx.commit();
                    }
                }

                if (val1 + bankSize != val2) {
                    if (numTries >= maxTries) {
                        String errMsg = "[SequenceUtil.SequenceBank.fillBank] maxTries (" + maxTries + ") reached for seqName [" + this.seqName + "], giving up.";
                        EntityUtilActivator.getInstance().logError(errMsg, null);
                        return;
                    }

                    // collision happened, wait a bounded random amount of time then continue
                    long waitTime = (long) (Math.random() * (maxWaitMillis - minWaitMillis) + minWaitMillis);

                    EntityUtilActivator.getInstance().logWarning("[SequenceUtil.SequenceBank.fillBank] Collision found for seqName [" + seqName + "], val1=" + val1 + ", val2=" + val2 + ", val1+bankSize=" + (val1 + bankSize) + ", bankSize=" + bankSize + ", waitTime=" + waitTime, null, null);

                    try {
                        // using the Thread.sleep to more reliably lock this thread: this.wait(waitTime);
                        java.lang.Thread.sleep(waitTime);
                    } catch (Exception e) {
                        EntityUtilActivator.getInstance().logWarning("Error waiting in sequence util", e, null);
                        return;
                    }
                }

                numTries++;
            }

            curSeqId = val1;
            maxSeqId = val2;
            EntityUtilActivator.getInstance().logInfo("Got bank of sequenced IDs for [" + this.seqName + "]; curSeqId=" + curSeqId + ", maxSeqId=" + maxSeqId + ", bankSize=" + bankSize);
        }

        private Connection getConnection() throws SQLException {
            String name = "osgi:service/(osgi.jndi.service.name=jdbc/NoTxTradeDataSource)";

            if (dataSource == null) {
                dataSource = (DataSource) ServiceUtil.getService(name);
            }

            if (dataSource == null) {
                EntityUtilActivator.getInstance().logError("Failed to lookup data source.");
                return null;
            }

            return dataSource.getConnection();

        }
    }
}
