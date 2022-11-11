import axios from 'axios'
import {CustomerQuery} from "./custome.query";

export class CustomerProviderApi {

    private readonly providerUrl

    constructor(rootUri: String = `${process.env.PROVIDER_CUSTOMER_ADDRESS}:${process.env.PROVIDER_CUSTOMER_PORT}`) {
        this.providerUrl = `${rootUri}/customers`
    }

    public async getAll(): Promise<CustomerQuery[]> {
        console.log(`ℹ️[server]: Querying customer data from [${this.providerUrl}]`);
        const { data } = await axios.get<CustomerQuery[]>(
            this.providerUrl, {
                headers: { Accept: 'application/json' }
            }
        )
        return data
    }
}