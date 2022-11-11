export class AggregatedCustomer {

    public name: string
    public aggregatedBalance: number

    constructor(name: string, aggregatedBalance: number) {
        this.name = name
        this.aggregatedBalance = aggregatedBalance
    }
}