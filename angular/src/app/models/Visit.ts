import { IShop, Shop } from './shop';
import { IVisitor } from './Visitor';

export interface IVisit {
    visitId: number,
    shop: IShop,
    visitor: IVisitor,
    dateTimeIn?: Date,
    dateTimeOut?: Date
}

export class Visit implements IVisit {
    visitId: number;
    shop: IShop;
    visitor: IVisitor;
    dateTimeIn: Date;
    dateTimeOut: Date;
}
