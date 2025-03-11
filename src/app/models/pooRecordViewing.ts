export class PooRecordViewing {
    id!: number;
    pooWhere!: string;
    pooType!: string;
    pooColor!: string;
    painBefore!: number;
    painDuring!: number;
    painAfter!: number;
    urgent!: boolean;
    laxative!: boolean;
    bleeding!: boolean;
    notes!: string;
    timestamp!: number[] | Date;
}