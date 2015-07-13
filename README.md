# README

This is a sample project of JUnit tests on advanced JPA features and CDI based on **Arquillian**

## DeliveryNoteServiceTest
Some tests on JPA: 

* *optimistic lock*
```
mvn clean test -Parq-wildfly-managed -Dtest=DeliveryNoteServiceTest#shouldThrowOptimisticLockException  -DfailIfNoTests=false
```
* *L2 cache*
```
mvn clean test -Parq-wildfly-managed -Dtest=DeliveryNoteServiceTest#shouldBeCached  -DfailIfNoTests=false
```
* *constructor queries* with Criteria API
```
mvn clean test -Parq-wildfly-managed -Dtest=DeliveryNoteServiceTest#shouldBeObjectArray  -DfailIfNoTests=false
mvn clean test -Parq-wildfly-managed -Dtest=DeliveryNoteServiceTest#shouldBeTuple  -DfailIfNoTests=false
mvn clean test -Parq-wildfly-managed -Dtest=DeliveryNoteServiceTest#shouldBeDTO  -DfailIfNoTests=false
```

## InvoiceServiceTest

Tests on *Pessimistick Lock* 
```
mvn clean test -Parq-wildfly-managed -Dtest=InvoiceServiceTest -DfailIfNoTests=false
```

## DeliveryNoteProcessorTest

Tests on *Programmatic Lookup* with CDI

```
mvn clean test -Parq-wildfly-managed -Dtest=DeliveryNoteProcessorTest  -DfailIfNoTests=false
```

## MockDeliveryNoteProcessorTest
 
Tests on CDI *Alternatives*
```
mvn clean test -Parq-wildfly-managed -Dtest=MockDeliveryNoteProcessorTest  -DfailIfNoTests=false
```

 
