/* tslint:disable */
import { LogEntryResource } from './log-entry-resource';
import { ResourceMetaData } from './resource-meta-data';
export interface LogEntriesResource {
  resource?: string;
  logEntries?: Array<LogEntryResource>;
  meta?: ResourceMetaData;
}
