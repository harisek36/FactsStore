package com.app360.harishsekar.facts360;

/**
 * Created by harishsekar on 7/20/17.
 */

public class FactsBroker {

    private String Broker_Facts;

    public FactsBroker(String broker_Facts) {
        Broker_Facts = broker_Facts;
    }

    public String getBroker_Facts() {
        return Broker_Facts;
    }

    public void setBroker_Facts(String broker_Facts) {
        Broker_Facts = broker_Facts;
    }

}
