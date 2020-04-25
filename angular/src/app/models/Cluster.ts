import { IVisit } from './Visit';

export interface ICluster {
    dateTimeIn: Date,
    dateTimeOut: Date,
    visits: IVisit[]
}

export class Cluster implements ICluster {
    dateTimeIn: Date;
    dateTimeOut: Date;
    visits: IVisit[] = [];
}