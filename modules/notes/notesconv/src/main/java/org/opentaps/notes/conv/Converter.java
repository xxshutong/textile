package org.opentaps.notes.conv;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.opentaps.notes.domain.Note;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.MongoURI;


public class Converter {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Options options = new Options();
        options.addOption("s", "source", true, "Source database URL in JDBC format");
        options.addOption("u", "username", true, "An username to access source database");
        options.addOption("p", "password", true, "A password to access source database");
        options.addOption("d", "dry-run", false, "Output to the screen w/o modifying MongoDB");
        options.addOption("m", "mongo", true, "Mongo URI. Default: mongodb://localhost");
        options.addOption("h", "help", false, "Print available options");

        CommandLineParser parser = new GnuParser();
        try {
            CommandLine cmd = parser.parse(options, args);

            if (cmd.hasOption("help")) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("java -jar notesconv.jar", options, true);
                return;
            }

            if (!cmd.hasOption("source")) {
                System.err.println("Please, define source database URL, e.g. jdbc:mysql://127.0.0.1/opentaps2");
                return;
            }
            String source = cmd.getOptionValue("source");

            // open connection to source database
            Properties props = new Properties();
            props.put("user", cmd.getOptionValue("u"));
            props.put("password", cmd.getOptionValue("p"));
            Connection c = DriverManager.getConnection(source, props);

            // open Mongo database and prepare collection
            String uri = cmd.getOptionValue("mongo");
            if (uri == null || uri.length() == 0) {
                uri = "mongodb://localhost";
            }
            Mongo mongo = new Mongo(new MongoURI(uri));
            DB db = mongo.getDB("notedb");
            DBCollection coll = db.getCollection("notes");

            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM NOTE_DATA;");
            while (rs.next()) {
                BasicDBObject noteDoc = (BasicDBObject) BasicDBObjectBuilder.start()
                        .add(Note.Fields.noteId.getName(), rs.getString("note_id"))
                        .add(Note.Fields.noteText.getName(), rs.getString("note_text"))
                        .add(Note.Fields.sequenceNum.getName(), Long.valueOf(0)).get();
                
                String createdByUserId = rs.getString("created_by_user_id");
                if (createdByUserId != null)
                    noteDoc.put("createdByUserId", createdByUserId);

                String userIdType = rs.getString("user_id_type");
                if (userIdType != null)
                    noteDoc.put("userIdType", userIdType);

                String clientDomain = rs.getString("client_domain");
                if (clientDomain != null)
                    noteDoc.put(Note.Fields.clientDomain.getName(), clientDomain);

                Timestamp dateTimeCreated = rs.getTimestamp("date_time_created");
                if (dateTimeCreated != null)
                    noteDoc.put(Note.Fields.dateTimeCreated.getName(), dateTimeCreated);

                for (int i = 1; i <= 10; i++) {
                    String attr = rs.getString(String.format("attribute_%1$d", i));
                    if (attr != null && attr.length() > 0) {
                        noteDoc.put(String.format("attribute%1$d", i), attr);
                    }
                }
                if (cmd.hasOption("dry-run")) {
                    System.out.println(noteDoc.toString());
                } else {
                    coll.insert(noteDoc);
                }
            }

        } catch (ParseException e) {
            System.err.println(e.getMessage());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (UnknownHostException e) {
            System.err.println(e.getMessage());
        } catch (MongoException e) {
            System.err.println(e.getMessage());
        }
    }
}
