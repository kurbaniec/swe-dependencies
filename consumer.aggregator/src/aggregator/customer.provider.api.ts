import axios from 'axios'
import {CustomerQuery} from "./custome.query";

export class CustomerProviderApi {

    // noinspection HttpUrlsUsage
    private providerUrl = `http://${process.env.PROVIDER_CUSTOMER_ADDRESS}:${process.env.PROVIDER_CUSTOMER_PORT}/customers`

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