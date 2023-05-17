import { ICluster } from '@/interfaces/cluster';
import { IMeta } from '@/interfaces/meta';
import { IUser } from '@/interfaces/user';

export interface ICourse extends IMeta {
  title: string;
  description: string;
  clusters: ICluster[];
  users: IUser[];
  deleted: boolean;
}
