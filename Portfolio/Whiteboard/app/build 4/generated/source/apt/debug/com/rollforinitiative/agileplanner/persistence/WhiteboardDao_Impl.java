package com.rollforinitiative.agileplanner.persistence;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.rollforinitiative.agileplanner.objects.Whiteboard;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class WhiteboardDao_Impl implements WhiteboardDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfWhiteboard;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public WhiteboardDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfWhiteboard = new EntityInsertionAdapter<Whiteboard>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `whiteboard_table`(`whiteboard_id`,`whiteboard_name`,`project_id`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Whiteboard value) {
        stmt.bindLong(1, value.getWhiteboardID());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        stmt.bindLong(3, value.getProjectID());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM whiteboard_table";
        return _query;
      }
    };
  }

  @Override
  public void insert(final Whiteboard whiteboard) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfWhiteboard.insert(whiteboard);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAll() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public LiveData<List<Whiteboard>> getAllForProject(final int projectID) {
    final String _sql = "SELECT * from whiteboard_table WHERE project_id = ? ORDER BY whiteboard_name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, projectID);
    return __db.getInvalidationTracker().createLiveData(new String[]{"whiteboard_table"}, false, new Callable<List<Whiteboard>>() {
      @Override
      public List<Whiteboard> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false);
        try {
          final int _cursorIndexOfWhiteboardID = CursorUtil.getColumnIndexOrThrow(_cursor, "whiteboard_id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "whiteboard_name");
          final int _cursorIndexOfProjectID = CursorUtil.getColumnIndexOrThrow(_cursor, "project_id");
          final List<Whiteboard> _result = new ArrayList<Whiteboard>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Whiteboard _item;
            final int _tmpWhiteboardID;
            _tmpWhiteboardID = _cursor.getInt(_cursorIndexOfWhiteboardID);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final int _tmpProjectID;
            _tmpProjectID = _cursor.getInt(_cursorIndexOfProjectID);
            _item = new Whiteboard(_tmpWhiteboardID,_tmpName,_tmpProjectID);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }
}
