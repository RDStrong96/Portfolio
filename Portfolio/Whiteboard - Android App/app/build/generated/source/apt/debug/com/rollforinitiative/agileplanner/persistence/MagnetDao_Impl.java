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
import com.rollforinitiative.agileplanner.objects.Magnet;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class MagnetDao_Impl implements MagnetDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfMagnet;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public MagnetDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMagnet = new EntityInsertionAdapter<Magnet>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `magnet_table`(`magnet_id`,`xPosition`,`yPosition`,`imageName`,`whiteboard_id`,`mBackgroundTintHelper`,`mImageHelper`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Magnet value) {
        stmt.bindLong(1, value.getMagnetID());
        stmt.bindDouble(2, value.xPosition);
        stmt.bindDouble(3, value.yPosition);
        if (value.getImageName() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getImageName());
        }
        stmt.bindLong(5, value.getWhiteboardID());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM magnet_table";
        return _query;
      }
    };
  }

  @Override
  public void insert(final Magnet magnet) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfMagnet.insert(magnet);
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
  public LiveData<List<Magnet>> getAllForWhiteboard(final int whiteboardID) {
    final String _sql = "SELECT * from magnet_table WHERE whiteboard_id = ? ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, whiteboardID);
    return __db.getInvalidationTracker().createLiveData(new String[]{"magnet_table"}, false, new Callable<List<Magnet>>() {
      @Override
      public List<Magnet> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false);
        try {
          final int _cursorIndexOfMagnetID = CursorUtil.getColumnIndexOrThrow(_cursor, "magnet_id");
          final int _cursorIndexOfXPosition = CursorUtil.getColumnIndexOrThrow(_cursor, "xPosition");
          final int _cursorIndexOfYPosition = CursorUtil.getColumnIndexOrThrow(_cursor, "yPosition");
          final int _cursorIndexOfImageName = CursorUtil.getColumnIndexOrThrow(_cursor, "imageName");
          final int _cursorIndexOfWhiteboardID = CursorUtil.getColumnIndexOrThrow(_cursor, "whiteboard_id");
          final int _cursorIndexOfMBackgroundTintHelper = CursorUtil.getColumnIndexOrThrow(_cursor, "mBackgroundTintHelper");
          final int _cursorIndexOfMImageHelper = CursorUtil.getColumnIndexOrThrow(_cursor, "mImageHelper");
          final List<Magnet> _result = new ArrayList<Magnet>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Magnet _item;
            _item = new Magnet();
            _item.getMagnetID = _cursor.getInt(_cursorIndexOfMagnetID);
            _item.xPosition = _cursor.getFloat(_cursorIndexOfXPosition);
            _item.yPosition = _cursor.getFloat(_cursorIndexOfYPosition);
            _item.getImageName = _cursor.getString(_cursorIndexOfImageName);
            _item.getWhiteboardID = _cursor.getInt(_cursorIndexOfWhiteboardID);
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
