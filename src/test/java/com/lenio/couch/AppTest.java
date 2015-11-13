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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sun.security.provider.SHA;

import java.math.BigDecimal;
import java.time.Clock;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static java.util.Arrays.asList;
import static org.joda.money.CurrencyUnit.USD;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AppTest {


    Cluster cluster;
    Bucket bucket;

    @Before
    public void setUp(){
        CouchbaseEnvironment env = DefaultCouchbaseEnvironment.create();
        cluster = CouchbaseCluster.create(env, asList("http://192.168.99.100"));
        bucket = cluster.openBucket("transactions");
    }

    @After
    public void cleanUp(){
        cluster.disconnect();
    }

    @Test
    public void shouldAddAnotherUpdatedAtUTCEpochToUpdatedAtArray(){

        // given

        final String transactionId = UUID.randomUUID().toString();
        final SHA sha1 = new SHA();
        final String accountId = sha1.toString();
        Money amount = Money.of(USD, new BigDecimal("1.00"));

        final long now = Clock.systemUTC().millis();

        JsonObject transaction = JsonObject.empty()
                .put("accountId", accountId)
                .put("amount", amount.getAmount().toPlainString())
                .put("dateTime", now);

        // when

        JsonDocument doc = JsonDocument.create(transactionId, transaction);
        bucket.upsert(doc);


        // then

        final JsonDocument json = bucket.get(transactionId);
        final JsonObject content = json.content();
        final String pTransactionId = json.id();
        final String pAccountId = content.getString("accountId");
        final Money pAmount = Money.of(USD, new BigDecimal(content.getString("amount")));
        final Long pDateTime = content.getLong("dateTime");

        assertEquals("transactionId are not equal",transactionId, pTransactionId);
        assertEquals(amount, pAmount);
        assertEquals(accountId, pAccountId);
        assertTrue(now == pDateTime);
    }
}
