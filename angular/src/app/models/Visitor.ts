import { IVisit } from './Visit';

export interface IVisitor {
    visitorId: number,
    visits: IVisit[],
    name?: string,
    nric: string
}

export class Visitor implements IVisitor {
    visitorId: number;
    visits: IVisit[];
    name: string;
    nric: string
}