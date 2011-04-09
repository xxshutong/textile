/*
 * Copyright (c) 2011 Open Source Strategies, Inc.
 * 
 * Opentaps is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Opentaps is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Opentaps.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.opentaps.entity.model;

import java.util.Date;


public interface IUserLogin {

    public String getUserLoginId();

    public void setUserLoginId(String userLoginId);

    public String getCurrentPassword();

    public void setCurrentPassword(String currentPassword);

    public String getPasswordHint();

    public void setPasswordHint(String passwordHint);

    public String getIsSystem();

    public void setIsSystem(String isSystem);

    public String getEnabled();

    public void setEnabled(String enabled);

    public String getHasLoggedOut();

    public void setHasLoggedOut(String hasLoggedOut);

    public String getRequirePasswordChange();

    public void setRequirePasswordChange(String requirePasswordChange);

    public String getLastCurrencyUom();

    public void setLastCurrencyUom(String lastCurrencyUom);

    public String getLastLocale();

    public void setLastLocale(String lastLocale);

    public String getLastTimeZone();

    public void setLastTimeZone(String lastTimeZone);

    public Date getDisabledDateTime();

    public void setDisabledDateTime(Date disabledDateTime);

    public Long getSuccessiveFailedLogins();

    public void setSuccessiveFailedLogins(Long successiveFailedLogins);

    public String getExternalAuthId();

    public void setExternalAuthId(String externalAuthId);

    public String getUserLdapDn();

    public void setUserLdapDn(String userLdapDn);

    public String getPartyId();

    public void setPartyId(String partyId);

}
