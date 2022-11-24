package com.example.bilabonnement.repository;

import java.util.List;

public interface IGenericRepository<T> { //HUSK GENERISK DATATYPE <E>
  //gøres så det er dynamisk, alle datatyper kan indsættes
  //Claus bruger <E> fordi det gøres i Lister men han ville normalt bruge <T>
  //Kan bruge alle bogstaver, også V, men E er standard

  void create(T p);

  List<T> readAll();

  T read(int id); //dynamisk returtype

  void update(T p);

  void delete(int id);

}
