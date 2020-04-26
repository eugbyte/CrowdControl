import { Pipe, PipeTransform } from '@angular/core';
import { IVisit } from '../models/Visit';

@Pipe({
  name: 'visitPipe'
})
export class VisitPipe implements PipeTransform {

  transform(visit: IVisit): string {
    let description: string = `${visit.visitor.name}(${visit.visitor.nric}) `;
    description += `${visit.dateTimeIn} -> ${visit.dateTimeOut} `;
    return description;
  }

}
