package com.upp.api.composite.product;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ServiceAddresses {
    private final String cmp;
    private final String pro;
    private final String rev;
    private final String rec;

    public ServiceAddresses() {
        cmp = null;
        pro = null;
        rev = null;
        rec = null;
    }

}
