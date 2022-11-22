package com.example.bilabonnement.repository;

import java.util.List;

public interface IGenericRepository<E> { //HUSK GENERISK DATATYPE <E>
  //gøres så det er dynamisk, alle datatyper kan indsættes
  //Claus bruger <E> fordi det gøres i Lister men han ville normalt bruge <T>
  //Kan bruge alle bogstaver, også V, men E er standard

  void create(E p);

  List<E> readAll();

  E read(); //dynamisk returtype

  void update(E p);

  void delete(int id);

}
