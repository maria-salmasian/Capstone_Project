import { IMeta } from '@/interfaces/meta';
import { IRole } from '@/interfaces/role';

export interface IUser extends IMeta {
  username: string;
  name: string;
  lastName: string;
  enabled: boolean;
  role: {
    id: number;
    roleName: IRole;
  };
}
