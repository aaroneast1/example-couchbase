package com.lenio.couch;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;
import org.joda.money.Money;
import sun.security.provider.SHA;

import java.math.BigDecimal;
import java.time.Clock;
import java.util.UUID;

import static java.util.Arrays.asList;
import static org.joda.money.CurrencyUnit.USD;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {
        System.out.println("hello world!");

    }
}
