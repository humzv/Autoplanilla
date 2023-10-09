import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { ICompany } from 'app/shared/model/company.model';
import { IPlanilla } from 'app/shared/model/planilla.model';
import { IdentificationType } from 'app/shared/model/enumerations/identification-type.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';
import { JornadaType } from 'app/shared/model/enumerations/jornada-type.model';
import { Language } from 'app/shared/model/enumerations/language.model';

export interface IEmployee {
  id?: number;
  firstName?: string;
  lastName?: string;
  lastName2?: string | null;
  email?: string;
  phoneNumber?: string | null;
  nationality?: string;
  identification?: string;
  identificationType?: IdentificationType;
  insuredNumber?: string | null;
  hireDate?: string;
  birthDate?: string | null;
  gender?: Gender | null;
  civilStatus?: string | null;
  countryOfBirth?: string | null;
  salary?: number;
  jornada?: JornadaType;
  language?: Language | null;
  daysOfShift?: number | null;
  hoursOfShift?: number | null;
  jobCode?: number | null;
  costCenter?: string | null;
  user?: IUser | null;
  company?: ICompany | null;
  planilla?: IPlanilla | null;
}

export const defaultValue: Readonly<IEmployee> = {};
