
export type CustomerQuery = {
    name: string,
    products: ProductQuery[]
}

export type ProductQuery = {
    balance: number
}