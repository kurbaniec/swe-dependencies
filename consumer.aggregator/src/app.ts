import express, { Express, Request, Response } from 'express';
import dotenv from 'dotenv';
import {AggregatorService} from "./aggregator/aggregator.service";

dotenv.config();

const app: Express = express();
const port = process.env.PORT;
const service = new AggregatorService()

app.get('/customers', async (req: Request, res: Response) => {
    const customers = await service.getAllAggregatedCustomers()
    res.send(customers);
});

app.listen(port, () => {
    console.log(`⚡️[server]: Server is running at https://localhost:${port}`);
});