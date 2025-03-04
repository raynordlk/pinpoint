/*
 * Copyright 2019 NAVER Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.navercorp.pinpoint.profiler.context.grpc;

import com.google.protobuf.ByteString;
import com.google.protobuf.GeneratedMessageV3;
import com.navercorp.pinpoint.common.annotations.VisibleForTesting;
import com.navercorp.pinpoint.common.profiler.message.MessageConverter;
import com.navercorp.pinpoint.grpc.trace.PApiMetaData;
import com.navercorp.pinpoint.grpc.trace.PSqlMetaData;
import com.navercorp.pinpoint.grpc.trace.PSqlUidMetaData;
import com.navercorp.pinpoint.grpc.trace.PStringMetaData;
import com.navercorp.pinpoint.profiler.metadata.ApiMetaData;
import com.navercorp.pinpoint.profiler.metadata.MetaDataType;
import com.navercorp.pinpoint.profiler.metadata.SqlMetaData;
import com.navercorp.pinpoint.profiler.metadata.SqlUidMetaData;
import com.navercorp.pinpoint.profiler.metadata.StringMetaData;

/**
 * @author jaehong.kim
 */
public class GrpcMetadataMessageConverter implements MessageConverter<MetaDataType, GeneratedMessageV3> {


    public GrpcMetadataMessageConverter() {

    }

    @Override
    public GeneratedMessageV3 toMessage(MetaDataType message) {
        if (message instanceof SqlMetaData) {
            final SqlMetaData sqlMetaData = (SqlMetaData) message;
            return convertSqlMetaData(sqlMetaData);
        } else if (message instanceof SqlUidMetaData) {
            final SqlUidMetaData sqlUidMetaData = (SqlUidMetaData) message;
            return convertSqlUidMetaData(sqlUidMetaData);
        } else if (message instanceof ApiMetaData) {
            final ApiMetaData apiMetaData = (ApiMetaData) message;
            return convertApiMetaData(apiMetaData);
        } else if (message instanceof StringMetaData) {
            final StringMetaData stringMetaData = (StringMetaData) message;
            return convertStringMetaData(stringMetaData);
        }
        return null;
    }

    @VisibleForTesting
    PSqlMetaData convertSqlMetaData(final SqlMetaData sqlMetaData) {
        final PSqlMetaData.Builder builder = PSqlMetaData.newBuilder();
        builder.setSqlId(sqlMetaData.getSqlId());
        builder.setSql(sqlMetaData.getSql());
        return builder.build();
    }

    @VisibleForTesting
    PSqlUidMetaData convertSqlUidMetaData(final SqlUidMetaData sqlUidMetaData) {
        PSqlUidMetaData.Builder builder = PSqlUidMetaData.newBuilder();
        builder.setSqlUid(ByteString.copyFrom(sqlUidMetaData.getSqlUid()));
        builder.setSql(sqlUidMetaData.getSql());
        return builder.build();
    }

    @VisibleForTesting
    PApiMetaData convertApiMetaData(final ApiMetaData apiMetaData) {
        final PApiMetaData.Builder builder = PApiMetaData.newBuilder();
        builder.setApiId(apiMetaData.getApiId());
        builder.setApiInfo(apiMetaData.getApiInfo());
        builder.setLine(apiMetaData.getLine());
        builder.setType(apiMetaData.getType());
        return builder.build();
    }

    @VisibleForTesting
    PStringMetaData convertStringMetaData(final StringMetaData stringMetaData) {
        final PStringMetaData.Builder builder = PStringMetaData.newBuilder();
        builder.setStringId(stringMetaData.getStringId());
        builder.setStringValue(stringMetaData.getStringValue());
        return builder.build();
    }
}