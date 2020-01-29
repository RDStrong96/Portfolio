package com.rollforinitiative.agileplanner.persistence;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class WhiteboardRoomDatabase_Impl extends WhiteboardRoomDatabase {
  private volatile ProjectDao _projectDao;

  private volatile WhiteboardDao _whiteboardDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `project_table` (`project_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `project_title` TEXT, `project_description` TEXT NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `whiteboard_table` (`whiteboard_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `whiteboard_name` TEXT NOT NULL, `project_id` INTEGER NOT NULL, FOREIGN KEY(`whiteboard_id`) REFERENCES `project_table`(`project_id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '3c7090f43bdf6f3405485317557fa465')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `project_table`");
        _db.execSQL("DROP TABLE IF EXISTS `whiteboard_table`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        _db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsProjectTable = new HashMap<String, TableInfo.Column>(3);
        _columnsProjectTable.put("project_id", new TableInfo.Column("project_id", "INTEGER", true, 1));
        _columnsProjectTable.put("project_title", new TableInfo.Column("project_title", "TEXT", false, 0));
        _columnsProjectTable.put("project_description", new TableInfo.Column("project_description", "TEXT", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysProjectTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesProjectTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoProjectTable = new TableInfo("project_table", _columnsProjectTable, _foreignKeysProjectTable, _indicesProjectTable);
        final TableInfo _existingProjectTable = TableInfo.read(_db, "project_table");
        if (! _infoProjectTable.equals(_existingProjectTable)) {
          throw new IllegalStateException("Migration didn't properly handle project_table(com.rollforinitiative.agileplanner.objects.Project).\n"
                  + " Expected:\n" + _infoProjectTable + "\n"
                  + " Found:\n" + _existingProjectTable);
        }
        final HashMap<String, TableInfo.Column> _columnsWhiteboardTable = new HashMap<String, TableInfo.Column>(3);
        _columnsWhiteboardTable.put("whiteboard_id", new TableInfo.Column("whiteboard_id", "INTEGER", true, 1));
        _columnsWhiteboardTable.put("whiteboard_name", new TableInfo.Column("whiteboard_name", "TEXT", true, 0));
        _columnsWhiteboardTable.put("project_id", new TableInfo.Column("project_id", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysWhiteboardTable = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysWhiteboardTable.add(new TableInfo.ForeignKey("project_table", "CASCADE", "NO ACTION",Arrays.asList("whiteboard_id"), Arrays.asList("project_id")));
        final HashSet<TableInfo.Index> _indicesWhiteboardTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoWhiteboardTable = new TableInfo("whiteboard_table", _columnsWhiteboardTable, _foreignKeysWhiteboardTable, _indicesWhiteboardTable);
        final TableInfo _existingWhiteboardTable = TableInfo.read(_db, "whiteboard_table");
        if (! _infoWhiteboardTable.equals(_existingWhiteboardTable)) {
          throw new IllegalStateException("Migration didn't properly handle whiteboard_table(com.rollforinitiative.agileplanner.objects.Whiteboard).\n"
                  + " Expected:\n" + _infoWhiteboardTable + "\n"
                  + " Found:\n" + _existingWhiteboardTable);
        }
      }
    }, "3c7090f43bdf6f3405485317557fa465", "232776def43840109d275aecba76ba36");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "project_table","whiteboard_table");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `project_table`");
      _db.execSQL("DELETE FROM `whiteboard_table`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public ProjectDao projectDao() {
    if (_projectDao != null) {
      return _projectDao;
    } else {
      synchronized(this) {
        if(_projectDao == null) {
          _projectDao = new ProjectDao_Impl(this);
        }
        return _projectDao;
      }
    }
  }

  @Override
  public WhiteboardDao whiteboardDao() {
    if (_whiteboardDao != null) {
      return _whiteboardDao;
    } else {
      synchronized(this) {
        if(_whiteboardDao == null) {
          _whiteboardDao = new WhiteboardDao_Impl(this);
        }
        return _whiteboardDao;
      }
    }
  }
}
