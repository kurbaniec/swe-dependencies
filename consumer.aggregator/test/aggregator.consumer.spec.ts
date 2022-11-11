import nock from "nock"
import portFinder from "portfinder"
import {AggregatorService} from "../src/aggregator/aggregator.service";
import {CustomerProviderApi} from "../src/aggregator/customer.provider.api";
import {AggregatedCustomer} from "../src/aggregator/customer";
import {expect} from "chai";

describe("API test", () => {
    it("get all customers", async () => {
        const port = await portFinder.getPortPromise()
        const url = `http://localhost:${port}`
        nock(url)
            .get("/customers")
            .reply(200, consumerData)
        const service = new AggregatorService(new CustomerProviderApi(url))
        const expected = [
            new AggregatedCustomer("Max Mustermann", 100),
            new AggregatedCustomer("Maxine Mustermann", 1000),
            new AggregatedCustomer("John Doe", 700)
        ]

        const customers = await service.getAllAggregatedCustomers()

        expect(customers).to.deep.equal(expected)
    })
})

const consumerData = [
    {
        "email": "customer.a@gmail.com",
        "firstname": "Max",
        "lastname": "Mustermann",
        "birthday": "2000-01-01",
        "address": {
            "street": "Höchstädtpl. 6",
            "city": "Vienna",
            "zip": "1200",
            "country": "AT"
        },
        "products": [
            {
                "code": "550e8400-e29b-11d4-a716-446655440000",
                "name": "A",
                "balance": 100,
                "interestRate": 5
            }
        ],
        "status": "CREATED",
        "name": "Max Mustermann"
    },
    {
        "email": "customer.b@gmail.com",
        "firstname": "Maxine",
        "lastname": "Mustermann",
        "birthday": "2001-01-01",
        "address": {
            "street": "Friedrich-Schmidt-Platz 1",
            "city": "Vienna",
            "zip": "1010",
            "country": "AT"
        },
        "products": [
            {
                "code": "550e8400-e29b-11d4-a716-446655440001",
                "name": "B",
                "balance": 1000,
                "interestRate": 1
            }
        ],
        "status": "VERIFIED",
        "name": "Maxine Mustermann"
    },
    {
        "email": "customer.c@gmail.com",
        "firstname": "John",
        "lastname": "Doe",
        "birthday": "1999-01-01",
        "address": {
            "street": "Gablenzg. 11",
            "city": "Vienna",
            "zip": "1150",
            "country": "AT"
        },
        "products": [
            {
                "code": "550e8400-e29b-11d4-a716-446655440002",
                "name": "C",
                "balance": 500,
                "interestRate": 3.5
            },
            {
                "code": "550e8400-e29b-11d4-a716-446655440002",
                "name": "C",
                "balance": 200,
                "interestRate": 1.5
            }
        ],
        "status": "BANNED",
        "name": "John Doe"
    }
]