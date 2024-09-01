package com.sparta.aiverification.menu.entity;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.StringPath;

public class QMenu extends EntityPathBase<Menu> {

  public static final QMenu menu = new QMenu("menu");

  public final StringPath id = createString("id");
  public final StringPath name = createString("name");
  public final StringPath description = createString("description");

  public QMenu(String variable) {
    super(Menu.class, forVariable(variable));
  }

  public QMenu(Path<? extends Menu> path) {
    super(path.getType(), path.getMetadata());
  }

  public QMenu(PathMetadata metadata) {
    super(Menu.class, metadata);
  }

}
