/* tslint:disable */
import { LogEntriesResource } from './log-entries-resource';
import { PartsResource } from './parts-resource';
import { ParticipantsResource } from './participants-resource';
import { RelationshipsResource } from './relationships-resource';
import { OperationPatternSetsResource } from './operation-pattern-sets-resource';
import { ResourceMetaData } from './resource-meta-data';
export interface RunnerResource {
  resource?: string;
  log?: LogEntriesResource;
  parts?: PartsResource;
  label?: string;
  participants?: ParticipantsResource;
  relationships?: RelationshipsResource;
  allowedOperations?: OperationPatternSetsResource;
  meta?: ResourceMetaData;
}
