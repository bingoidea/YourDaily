package com.example.zdm.yourdaily.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.example.zdm.yourdaily.db.entity.MemoEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "MEMO_ENTITY".
*/
public class MemoEntityDao extends AbstractDao<MemoEntity, Long> {

    public static final String TABLENAME = "MEMO_ENTITY";

    /**
     * Properties of entity MemoEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Memo = new Property(1, String.class, "memo", false, "MEMO");
        public final static Property MemoId = new Property(2, Long.class, "memoId", false, "MEMO_ID");
        public final static Property Date = new Property(3, java.util.Date.class, "date", false, "DATE");
        public final static Property Line = new Property(4, boolean.class, "line", false, "LINE");
    }


    public MemoEntityDao(DaoConfig config) {
        super(config);
    }
    
    public MemoEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"MEMO_ENTITY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"MEMO\" TEXT," + // 1: memo
                "\"MEMO_ID\" INTEGER," + // 2: memoId
                "\"DATE\" INTEGER," + // 3: date
                "\"LINE\" INTEGER NOT NULL );"); // 4: line
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"MEMO_ENTITY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, MemoEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String memo = entity.getMemo();
        if (memo != null) {
            stmt.bindString(2, memo);
        }
 
        Long memoId = entity.getMemoId();
        if (memoId != null) {
            stmt.bindLong(3, memoId);
        }
 
        java.util.Date date = entity.getDate();
        if (date != null) {
            stmt.bindLong(4, date.getTime());
        }
        stmt.bindLong(5, entity.getLine() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, MemoEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String memo = entity.getMemo();
        if (memo != null) {
            stmt.bindString(2, memo);
        }
 
        Long memoId = entity.getMemoId();
        if (memoId != null) {
            stmt.bindLong(3, memoId);
        }
 
        java.util.Date date = entity.getDate();
        if (date != null) {
            stmt.bindLong(4, date.getTime());
        }
        stmt.bindLong(5, entity.getLine() ? 1L: 0L);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public MemoEntity readEntity(Cursor cursor, int offset) {
        MemoEntity entity = new MemoEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // memo
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2), // memoId
            cursor.isNull(offset + 3) ? null : new java.util.Date(cursor.getLong(offset + 3)), // date
            cursor.getShort(offset + 4) != 0 // line
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, MemoEntity entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setMemo(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setMemoId(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
        entity.setDate(cursor.isNull(offset + 3) ? null : new java.util.Date(cursor.getLong(offset + 3)));
        entity.setLine(cursor.getShort(offset + 4) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(MemoEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(MemoEntity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(MemoEntity entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
