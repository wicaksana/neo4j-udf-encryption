# neo4j-udf-encryption

# How to 

1. Compile & package as a JAR file
2. Put the JAR file into $NEO4J_HOME/plugins
3. Restart Neo4j 

## Encrypting a text - Example

Cypher query:
```
match (h:Hero) return org.example.encrypt(h.name) as encrypted_name
```

Output:
```
╒══════════════════════════════════════════════════════════════════════╕
│encrypted_name                                                        │
╞══════════════════════════════════════════════════════════════════════╡
│"GJXQh+sFVUAHbRTNx4nEZa1ijlE5O7M34hcWz4Bud7M2EaIrTcPLxoRseQolaQTkHvgPK│
│6/gDOnB+/eCdAvTpOybWkdtqyMvbdpS/1OQrM3Kgvq9c/dSZ+GzDTeUdkXIkdvvUt7R6Lo│
│aMHZJV66Lczmw9O0gYWJniqK0de6tbM2/dYPG2IQ5q9wt2zW1hNImKEo+YlLnzrLOAZ82H│
│xkYAzXbmXJAnHmkskv1ATau5jQxsnRdctcyUYZ/MCGlykver06rWr9OytLMUKx+HU3CGbf│
│GkKwzvHOwRZt4GWn6Sm1x3SYaSwHqTYPa452pi+X18OHqAXu3hdqT5T746NcQHg=="    │
├──────────────────────────────────────────────────────────────────────┤
│"QOZrgUFYLJZ7aueKHZVriOMMqng1724IOmtVly4bIGuPvJ8fkLfrBxfPtaxX88h/wQtlk│
│ITKUbDqtXx/8Pbw1e0QUz+ySP5TS0KGriqZ/YmDp3FddnhMq1V7aDAIX1vUk22l55mqnW2│
│HJ9B73sU4iRT8QeRHh0J7j2T2BwjAG0Y2aytYW+976suhqliNg4iDQofitbDJx5fRgoIJb│
│dFAAawCI0ocHvp2kSQPYldw5Ueve5kYly0BVXep1QabNqRHlpJe9LS+g87j4weD9u+QFfn│
│hIidokGNZiCUIGkuKbhYUMnwGMUUEBQSgUtiDAJZaZ81fEgO+MB0458JJQ1bX5A=="    │
└──────────────────────────────────────────────────────────────────────┘

```

## Decrypting a cipher text - Example

Cypher query:
```
match (h:Hero)
with h.name as name,org.example.encrypt(h.name) AS encrypted_Text
return name, org.example.decrypt(encrypted_Text) as decrypted_Text
```

Output:
```
╒═══════════╤══════════════╕
│name       │decrypted_Text│
╞═══════════╪══════════════╡
│"Batman"   │"Batman"      │
├───────────┼──────────────┤
│"Daredevil"│"Daredevil"   │
└───────────┴──────────────┘
```




