import { IShop, Shop } from './shop';

export interface IVisit {
    visitId: number,
    shop: IShop
}

export class Visit implements IVisit {
    public visitId: number;
    public shop: IShop
}
