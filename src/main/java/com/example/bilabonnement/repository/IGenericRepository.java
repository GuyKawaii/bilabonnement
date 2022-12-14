package com.example.bilabonnement.repository;

import java.util.List;

/**
 * @author Veronica(Rhod1um)
 */
public interface IGenericRepository<T> {

  void create(T p);

  List<T> readAll();

  T read(int id); //dynamisk returtype

  void update(T p);

  void delete(int id);
}
