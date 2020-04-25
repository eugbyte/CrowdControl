import { IVisit } from './Visit';

export interface IShop {
    shopId: number,
    name: string,
    visits: IVisit[]
}
export class Shop implements IShop {
    shopId: number;
    name: string;
    visits: IVisit[] = [];
}