import path from "path"
import {MatchersV3, PactV3, SpecificationVersion,} from "@pact-foundation/pact"
import {AggregatorService} from "../src/aggregator/aggregator.service";
import {CustomerProviderApi} from "../src/aggregator/customer.provider.api";
import {AggregatedCustomer} from "../src/aggregator/customer";
import {atLeastLike, number, string} from "@pact-foundation/pact/src/v3/matchers";
import {expect} from "chai";

const {eachLike} = MatchersV3

const provider = new PactV3({
    consumer: "AggregatorService",
    provider: "CustomerService",
    logLevel: "warn",
    dir: path.resolve(process.cwd(), "pacts"),
    spec: SpecificationVersion.SPECIFICATION_VERSION_V3
})

describe("API Pact test", () => {
    describe("get all customers", () => {
        it("customers exist", async () => {
            provider.addInteraction({
                states: [{description: "customers exist"}],
                uponReceiving: "get all customers",
                withRequest: {
                    method: "GET",
                    path: "/customers"
                },
                willRespondWith: {
                    status: 200,
                    headers: {"Content-Type": "application/json"},
                    body: atLeastLike({
                        name: string("John Doe"),
                        products: eachLike({
                            balance: number(1000)
                        })
                    }, 1)
                },
            })

            await provider.executeTest(async (mockService) => {
                const service = new AggregatorService(new CustomerProviderApi(mockService.url))

                const customers = await service.getAllAggregatedCustomers()

                expect(customers).to.deep.equal([
                    new AggregatedCustomer("John Doe", 1000)
                ])
            })
        })

        it("no customers exist", async () => {
            provider.addInteraction({
                states: [{description: "no customers exist"}],
                uponReceiving: "get all customers",
                withRequest: {
                    method: "GET",
                    path: "/customers"
                },
                willRespondWith: {
                    status: 200,
                    headers: {"Content-Type": "application/json"},
                    body: []
                },
            })

            await provider.executeTest(async (mockService) => {
                const service = new AggregatorService(new CustomerProviderApi(mockService.url))

                const customers = await service.getAllAggregatedCustomers()

                expect(customers).to.deep.equal([])
            })
        })
    })
})


