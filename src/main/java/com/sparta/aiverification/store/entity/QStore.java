package com.sparta.aiverification.store.entity;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;

public class QStore extends EntityPathBase<Store> {

  private static final long serialVersionUID = 1L;

  public static final QStore store = new QStore("store");

  public final NumberPath<Long> id = createNumber("id", Long.class);
  public final StringPath name = createString("name");
  public final NumberPath<Long> regionId = createNumber("regionId", Long.class);
  public final NumberPath<Long> categoryId = createNumber("categoryId", Long.class);
  public final StringPath description = createString("description");

  public QStore(String variable) {
    super(Store.class, forVariable(variable));
  }

  public QStore(Path<? extends Store> path) {
    super(path.getType(), path.getMetadata());
  }

  public QStore(PathMetadata metadata) {
    super(Store.class, metadata);
  }
}
