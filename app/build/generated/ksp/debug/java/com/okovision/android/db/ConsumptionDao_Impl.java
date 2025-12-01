package com.okovision.android.db;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ConsumptionDao_Impl implements ConsumptionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ConsumptionEntity> __insertionAdapterOfConsumptionEntity;

  private final SharedSQLiteStatement __preparedStmtOfClear;

  public ConsumptionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfConsumptionEntity = new EntityInsertionAdapter<ConsumptionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `consumption` (`dateIso`,`kg`,`cost`) VALUES (?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ConsumptionEntity entity) {
        statement.bindString(1, entity.getDateIso());
        statement.bindDouble(2, entity.getKg());
        if (entity.getCost() == null) {
          statement.bindNull(3);
        } else {
          statement.bindDouble(3, entity.getCost());
        }
      }
    };
    this.__preparedStmtOfClear = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM consumption";
        return _query;
      }
    };
  }

  @Override
  public Object upsertAll(final List<ConsumptionEntity> items,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfConsumptionEntity.insert(items);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object clear(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClear.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfClear.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<ConsumptionEntity>> getAll() {
    final String _sql = "SELECT * FROM consumption ORDER BY dateIso ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"consumption"}, new Callable<List<ConsumptionEntity>>() {
      @Override
      @NonNull
      public List<ConsumptionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfDateIso = CursorUtil.getColumnIndexOrThrow(_cursor, "dateIso");
          final int _cursorIndexOfKg = CursorUtil.getColumnIndexOrThrow(_cursor, "kg");
          final int _cursorIndexOfCost = CursorUtil.getColumnIndexOrThrow(_cursor, "cost");
          final List<ConsumptionEntity> _result = new ArrayList<ConsumptionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ConsumptionEntity _item;
            final String _tmpDateIso;
            _tmpDateIso = _cursor.getString(_cursorIndexOfDateIso);
            final double _tmpKg;
            _tmpKg = _cursor.getDouble(_cursorIndexOfKg);
            final Double _tmpCost;
            if (_cursor.isNull(_cursorIndexOfCost)) {
              _tmpCost = null;
            } else {
              _tmpCost = _cursor.getDouble(_cursorIndexOfCost);
            }
            _item = new ConsumptionEntity(_tmpDateIso,_tmpKg,_tmpCost);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
