import {CustomerProviderApi} from "./customer.provider.api";
import {AggregatedCustomer} from "./customer";

export class AggregatorService {

    private api: CustomerProviderApi

    constructor(api: CustomerProviderApi = new CustomerProviderApi()) {
        this.api = api
    }

    public async getAllAggregatedCustomers(): Promise<AggregatedCustomer[]> {
        console.log(`ℹ️[server]: Querying aggregated customers`);
        const data = await this.api.getAll()
        const customers = data.map((c) => {
            const balance = c.products.reduce(
                (previousValue, currentValue) => previousValue + currentValue.balance, 0)
            return new AggregatedCustomer(c.name, balance)
        })
        console.log(`ℹ️[server]: Found ${customers.length}`);
        return customers
    }
}