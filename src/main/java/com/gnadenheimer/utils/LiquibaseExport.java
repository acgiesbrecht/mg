/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gnadenheimer.utils;

import com.gnadenheimer.mg.utils.Utils;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.diff.output.DiffOutputControl;
import liquibase.diff.output.StandardObjectChangeFilter;
import liquibase.exception.LiquibaseException;
import liquibase.integration.commandline.CommandLineUtils;

/**
 *
 * @author acgie
 */
public class LiquibaseExport {
    
    private static final Logger log = LogManager.getLogger(LiquibaseExport.class);
    private static final String AUTHOR = "liquibase-backuper";
    private static final String CHANGELOG_FILE_NAME_TEMPLATE = "\\backup_%s.%s%s";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
    
    public enum BackupFormat {
        XML, SQL
    }
    
    public enum SnapshotType {
        ;
        public static final String DATA = "data";
    }            
    
    public void backup() {
        log.info("Backing up data...");
        try (Connection connection = Utils.getInstance().getDatabaseConnection()) {
            doBackup(connection, SnapshotType.DATA, AUTHOR, makeDiffOutputControl());
        } catch (Exception e) {
            throw new RuntimeException("Error backing up data", e);
        }
        log.info("Backup completed");
    }

    
    private void doBackup(Connection connection, String snapshotTypes, String author, DiffOutputControl diffOutputControl) {
        try{            
            String fileName = generateChangeLog(connection, snapshotTypes, author, diffOutputControl);            
        }catch(IOException | ParserConfigurationException | LiquibaseException ex){
            log.error(ex.getMessage());
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    private String generateChangeLog(Connection connection, String snapshotTypes, String author, DiffOutputControl diffOutputControl) throws IOException, ParserConfigurationException, LiquibaseException {
        DatabaseConnection databaseConnection = new JdbcConnection(connection);        
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(databaseConnection);
        String fileName = makeChangeLogFileName(database);
        System.out.println(fileName);
        CommandLineUtils.doGenerateChangeLog(fileName, database, null, null, snapshotTypes, author, null, null, diffOutputControl);
        JOptionPane.showMessageDialog(null, "Completado con exito!");
        return fileName;
    }
        
    private String makeChangeLogFileName(Database database) {
        String fileId = LocalDateTime.now().format(DATE_TIME_FORMATTER);
        BackupFormat format = BackupFormat.SQL;
        String extension = format.name().toLowerCase();
        String databaseType = format == BackupFormat.SQL ? String.format("%s.", database.getShortName()) : "";
        String fileName = String.format(Preferences.userRoot().node("MG").get("Datadir", "") + CHANGELOG_FILE_NAME_TEMPLATE, fileId, databaseType, extension);
        JOptionPane.showMessageDialog(null, "Generando " + fileName);
        return fileName;
    }

    private DiffOutputControl makeDiffOutputControl() {
        DiffOutputControl diffOutputControl = new DiffOutputControl(false, false, false, null);
        
        /*List<String> tables = properties.getTables();
        if (tables != null && !tables.isEmpty()) {
            setTablesFilter(diffOutputControl, tables);
        }*/
        return diffOutputControl;
    }

    private void setTablesFilter(DiffOutputControl diffOutputControl, List<String> tables) {
        String tableNamesPattern = tables.stream()
                .map(t -> MessageFormat.format("table:(?i){0}", t))
                .collect(Collectors.joining(","));
        StandardObjectChangeFilter filter = new StandardObjectChangeFilter(StandardObjectChangeFilter.FilterType.INCLUDE, tableNamesPattern);
        diffOutputControl.setObjectChangeFilter(filter);
    }   
    
}
