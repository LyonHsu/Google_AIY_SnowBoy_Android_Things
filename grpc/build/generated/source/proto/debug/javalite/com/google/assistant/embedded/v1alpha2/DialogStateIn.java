// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/assistant/embedded/v1alpha2/embedded_assistant.proto

package com.google.assistant.embedded.v1alpha2;

/**
 * <pre>
 * Provides information about the current dialog state.
 * </pre>
 *
 * Protobuf type {@code google.assistant.embedded.v1alpha2.DialogStateIn}
 */
public  final class DialogStateIn extends
    com.google.protobuf.GeneratedMessageLite<
        DialogStateIn, DialogStateIn.Builder> implements
    // @@protoc_insertion_point(message_implements:google.assistant.embedded.v1alpha2.DialogStateIn)
    DialogStateInOrBuilder {
  private DialogStateIn() {
    conversationState_ = com.google.protobuf.ByteString.EMPTY;
    languageCode_ = "";
  }
  public static final int CONVERSATION_STATE_FIELD_NUMBER = 1;
  private com.google.protobuf.ByteString conversationState_;
  /**
   * <pre>
   * *Required* This field must always be set to the
   * [DialogStateOut.conversation_state][google.assistant.embedded.v1alpha2.DialogStateOut.conversation_state] value that was returned in the prior
   * `Assist` RPC. It should only be omitted (field not set) if there was no
   * prior `Assist` RPC because this is the first `Assist` RPC made by this
   * device after it was first setup and/or a factory-default reset.
   * </pre>
   *
   * <code>optional bytes conversation_state = 1;</code>
   */
  public com.google.protobuf.ByteString getConversationState() {
    return conversationState_;
  }
  /**
   * <pre>
   * *Required* This field must always be set to the
   * [DialogStateOut.conversation_state][google.assistant.embedded.v1alpha2.DialogStateOut.conversation_state] value that was returned in the prior
   * `Assist` RPC. It should only be omitted (field not set) if there was no
   * prior `Assist` RPC because this is the first `Assist` RPC made by this
   * device after it was first setup and/or a factory-default reset.
   * </pre>
   *
   * <code>optional bytes conversation_state = 1;</code>
   */
  private void setConversationState(com.google.protobuf.ByteString value) {
    if (value == null) {
    throw new NullPointerException();
  }
  
    conversationState_ = value;
  }
  /**
   * <pre>
   * *Required* This field must always be set to the
   * [DialogStateOut.conversation_state][google.assistant.embedded.v1alpha2.DialogStateOut.conversation_state] value that was returned in the prior
   * `Assist` RPC. It should only be omitted (field not set) if there was no
   * prior `Assist` RPC because this is the first `Assist` RPC made by this
   * device after it was first setup and/or a factory-default reset.
   * </pre>
   *
   * <code>optional bytes conversation_state = 1;</code>
   */
  private void clearConversationState() {
    
    conversationState_ = getDefaultInstance().getConversationState();
  }

  public static final int LANGUAGE_CODE_FIELD_NUMBER = 2;
  private java.lang.String languageCode_;
  /**
   * <pre>
   * *Required* Language of the request in
   * [IETF BCP 47 syntax](https://tools.ietf.org/html/bcp47). For example:
   * "en-US". If you have selected a language for this `device_id` using the
   * [Settings](https://developers.google.com/assistant/sdk/guides/assistant-settings)
   * menu in your phone's Google Assistant app, that selection will override
   * this value.
   * </pre>
   *
   * <code>optional string language_code = 2;</code>
   */
  public java.lang.String getLanguageCode() {
    return languageCode_;
  }
  /**
   * <pre>
   * *Required* Language of the request in
   * [IETF BCP 47 syntax](https://tools.ietf.org/html/bcp47). For example:
   * "en-US". If you have selected a language for this `device_id` using the
   * [Settings](https://developers.google.com/assistant/sdk/guides/assistant-settings)
   * menu in your phone's Google Assistant app, that selection will override
   * this value.
   * </pre>
   *
   * <code>optional string language_code = 2;</code>
   */
  public com.google.protobuf.ByteString
      getLanguageCodeBytes() {
    return com.google.protobuf.ByteString.copyFromUtf8(languageCode_);
  }
  /**
   * <pre>
   * *Required* Language of the request in
   * [IETF BCP 47 syntax](https://tools.ietf.org/html/bcp47). For example:
   * "en-US". If you have selected a language for this `device_id` using the
   * [Settings](https://developers.google.com/assistant/sdk/guides/assistant-settings)
   * menu in your phone's Google Assistant app, that selection will override
   * this value.
   * </pre>
   *
   * <code>optional string language_code = 2;</code>
   */
  private void setLanguageCode(
      java.lang.String value) {
    if (value == null) {
    throw new NullPointerException();
  }
  
    languageCode_ = value;
  }
  /**
   * <pre>
   * *Required* Language of the request in
   * [IETF BCP 47 syntax](https://tools.ietf.org/html/bcp47). For example:
   * "en-US". If you have selected a language for this `device_id` using the
   * [Settings](https://developers.google.com/assistant/sdk/guides/assistant-settings)
   * menu in your phone's Google Assistant app, that selection will override
   * this value.
   * </pre>
   *
   * <code>optional string language_code = 2;</code>
   */
  private void clearLanguageCode() {
    
    languageCode_ = getDefaultInstance().getLanguageCode();
  }
  /**
   * <pre>
   * *Required* Language of the request in
   * [IETF BCP 47 syntax](https://tools.ietf.org/html/bcp47). For example:
   * "en-US". If you have selected a language for this `device_id` using the
   * [Settings](https://developers.google.com/assistant/sdk/guides/assistant-settings)
   * menu in your phone's Google Assistant app, that selection will override
   * this value.
   * </pre>
   *
   * <code>optional string language_code = 2;</code>
   */
  private void setLanguageCodeBytes(
      com.google.protobuf.ByteString value) {
    if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
    
    languageCode_ = value.toStringUtf8();
  }

  public static final int DEVICE_LOCATION_FIELD_NUMBER = 5;
  private com.google.assistant.embedded.v1alpha2.DeviceLocation deviceLocation_;
  /**
   * <pre>
   * *Optional* Location of the device where the query originated.
   * </pre>
   *
   * <code>optional .google.assistant.embedded.v1alpha2.DeviceLocation device_location = 5;</code>
   */
  public boolean hasDeviceLocation() {
    return deviceLocation_ != null;
  }
  /**
   * <pre>
   * *Optional* Location of the device where the query originated.
   * </pre>
   *
   * <code>optional .google.assistant.embedded.v1alpha2.DeviceLocation device_location = 5;</code>
   */
  public com.google.assistant.embedded.v1alpha2.DeviceLocation getDeviceLocation() {
    return deviceLocation_ == null ? com.google.assistant.embedded.v1alpha2.DeviceLocation.getDefaultInstance() : deviceLocation_;
  }
  /**
   * <pre>
   * *Optional* Location of the device where the query originated.
   * </pre>
   *
   * <code>optional .google.assistant.embedded.v1alpha2.DeviceLocation device_location = 5;</code>
   */
  private void setDeviceLocation(com.google.assistant.embedded.v1alpha2.DeviceLocation value) {
    if (value == null) {
      throw new NullPointerException();
    }
    deviceLocation_ = value;
    
    }
  /**
   * <pre>
   * *Optional* Location of the device where the query originated.
   * </pre>
   *
   * <code>optional .google.assistant.embedded.v1alpha2.DeviceLocation device_location = 5;</code>
   */
  private void setDeviceLocation(
      com.google.assistant.embedded.v1alpha2.DeviceLocation.Builder builderForValue) {
    deviceLocation_ = builderForValue.build();
    
  }
  /**
   * <pre>
   * *Optional* Location of the device where the query originated.
   * </pre>
   *
   * <code>optional .google.assistant.embedded.v1alpha2.DeviceLocation device_location = 5;</code>
   */
  private void mergeDeviceLocation(com.google.assistant.embedded.v1alpha2.DeviceLocation value) {
    if (deviceLocation_ != null &&
        deviceLocation_ != com.google.assistant.embedded.v1alpha2.DeviceLocation.getDefaultInstance()) {
      deviceLocation_ =
        com.google.assistant.embedded.v1alpha2.DeviceLocation.newBuilder(deviceLocation_).mergeFrom(value).buildPartial();
    } else {
      deviceLocation_ = value;
    }
    
  }
  /**
   * <pre>
   * *Optional* Location of the device where the query originated.
   * </pre>
   *
   * <code>optional .google.assistant.embedded.v1alpha2.DeviceLocation device_location = 5;</code>
   */
  private void clearDeviceLocation() {  deviceLocation_ = null;
    
  }

  public static final int IS_NEW_CONVERSATION_FIELD_NUMBER = 7;
  private boolean isNewConversation_;
  /**
   * <pre>
   * *Optional* If true, the server will treat the request as a new conversation
   * and not use state from the prior request. Set this field to true when the
   * conversation should be restarted, such as after a device reboot, or after a
   * significant lapse of time since the prior query.
   * </pre>
   *
   * <code>optional bool is_new_conversation = 7;</code>
   */
  public boolean getIsNewConversation() {
    return isNewConversation_;
  }
  /**
   * <pre>
   * *Optional* If true, the server will treat the request as a new conversation
   * and not use state from the prior request. Set this field to true when the
   * conversation should be restarted, such as after a device reboot, or after a
   * significant lapse of time since the prior query.
   * </pre>
   *
   * <code>optional bool is_new_conversation = 7;</code>
   */
  private void setIsNewConversation(boolean value) {
    
    isNewConversation_ = value;
  }
  /**
   * <pre>
   * *Optional* If true, the server will treat the request as a new conversation
   * and not use state from the prior request. Set this field to true when the
   * conversation should be restarted, such as after a device reboot, or after a
   * significant lapse of time since the prior query.
   * </pre>
   *
   * <code>optional bool is_new_conversation = 7;</code>
   */
  private void clearIsNewConversation() {
    
    isNewConversation_ = false;
  }

  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!conversationState_.isEmpty()) {
      output.writeBytes(1, conversationState_);
    }
    if (!languageCode_.isEmpty()) {
      output.writeString(2, getLanguageCode());
    }
    if (deviceLocation_ != null) {
      output.writeMessage(5, getDeviceLocation());
    }
    if (isNewConversation_ != false) {
      output.writeBool(7, isNewConversation_);
    }
  }

  public int getSerializedSize() {
    int size = memoizedSerializedSize;
    if (size != -1) return size;

    size = 0;
    if (!conversationState_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(1, conversationState_);
    }
    if (!languageCode_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeStringSize(2, getLanguageCode());
    }
    if (deviceLocation_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(5, getDeviceLocation());
    }
    if (isNewConversation_ != false) {
      size += com.google.protobuf.CodedOutputStream
        .computeBoolSize(7, isNewConversation_);
    }
    memoizedSerializedSize = size;
    return size;
  }

  public static com.google.assistant.embedded.v1alpha2.DialogStateIn parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data);
  }
  public static com.google.assistant.embedded.v1alpha2.DialogStateIn parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }
  public static com.google.assistant.embedded.v1alpha2.DialogStateIn parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data);
  }
  public static com.google.assistant.embedded.v1alpha2.DialogStateIn parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }
  public static com.google.assistant.embedded.v1alpha2.DialogStateIn parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input);
  }
  public static com.google.assistant.embedded.v1alpha2.DialogStateIn parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }
  public static com.google.assistant.embedded.v1alpha2.DialogStateIn parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input);
  }
  public static com.google.assistant.embedded.v1alpha2.DialogStateIn parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
  }
  public static com.google.assistant.embedded.v1alpha2.DialogStateIn parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input);
  }
  public static com.google.assistant.embedded.v1alpha2.DialogStateIn parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.google.assistant.embedded.v1alpha2.DialogStateIn prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }

  /**
   * <pre>
   * Provides information about the current dialog state.
   * </pre>
   *
   * Protobuf type {@code google.assistant.embedded.v1alpha2.DialogStateIn}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageLite.Builder<
        com.google.assistant.embedded.v1alpha2.DialogStateIn, Builder> implements
      // @@protoc_insertion_point(builder_implements:google.assistant.embedded.v1alpha2.DialogStateIn)
      com.google.assistant.embedded.v1alpha2.DialogStateInOrBuilder {
    // Construct using com.google.assistant.embedded.v1alpha2.DialogStateIn.newBuilder()
    private Builder() {
      super(DEFAULT_INSTANCE);
    }


    /**
     * <pre>
     * *Required* This field must always be set to the
     * [DialogStateOut.conversation_state][google.assistant.embedded.v1alpha2.DialogStateOut.conversation_state] value that was returned in the prior
     * `Assist` RPC. It should only be omitted (field not set) if there was no
     * prior `Assist` RPC because this is the first `Assist` RPC made by this
     * device after it was first setup and/or a factory-default reset.
     * </pre>
     *
     * <code>optional bytes conversation_state = 1;</code>
     */
    public com.google.protobuf.ByteString getConversationState() {
      return instance.getConversationState();
    }
    /**
     * <pre>
     * *Required* This field must always be set to the
     * [DialogStateOut.conversation_state][google.assistant.embedded.v1alpha2.DialogStateOut.conversation_state] value that was returned in the prior
     * `Assist` RPC. It should only be omitted (field not set) if there was no
     * prior `Assist` RPC because this is the first `Assist` RPC made by this
     * device after it was first setup and/or a factory-default reset.
     * </pre>
     *
     * <code>optional bytes conversation_state = 1;</code>
     */
    public Builder setConversationState(com.google.protobuf.ByteString value) {
      copyOnWrite();
      instance.setConversationState(value);
      return this;
    }
    /**
     * <pre>
     * *Required* This field must always be set to the
     * [DialogStateOut.conversation_state][google.assistant.embedded.v1alpha2.DialogStateOut.conversation_state] value that was returned in the prior
     * `Assist` RPC. It should only be omitted (field not set) if there was no
     * prior `Assist` RPC because this is the first `Assist` RPC made by this
     * device after it was first setup and/or a factory-default reset.
     * </pre>
     *
     * <code>optional bytes conversation_state = 1;</code>
     */
    public Builder clearConversationState() {
      copyOnWrite();
      instance.clearConversationState();
      return this;
    }

    /**
     * <pre>
     * *Required* Language of the request in
     * [IETF BCP 47 syntax](https://tools.ietf.org/html/bcp47). For example:
     * "en-US". If you have selected a language for this `device_id` using the
     * [Settings](https://developers.google.com/assistant/sdk/guides/assistant-settings)
     * menu in your phone's Google Assistant app, that selection will override
     * this value.
     * </pre>
     *
     * <code>optional string language_code = 2;</code>
     */
    public java.lang.String getLanguageCode() {
      return instance.getLanguageCode();
    }
    /**
     * <pre>
     * *Required* Language of the request in
     * [IETF BCP 47 syntax](https://tools.ietf.org/html/bcp47). For example:
     * "en-US". If you have selected a language for this `device_id` using the
     * [Settings](https://developers.google.com/assistant/sdk/guides/assistant-settings)
     * menu in your phone's Google Assistant app, that selection will override
     * this value.
     * </pre>
     *
     * <code>optional string language_code = 2;</code>
     */
    public com.google.protobuf.ByteString
        getLanguageCodeBytes() {
      return instance.getLanguageCodeBytes();
    }
    /**
     * <pre>
     * *Required* Language of the request in
     * [IETF BCP 47 syntax](https://tools.ietf.org/html/bcp47). For example:
     * "en-US". If you have selected a language for this `device_id` using the
     * [Settings](https://developers.google.com/assistant/sdk/guides/assistant-settings)
     * menu in your phone's Google Assistant app, that selection will override
     * this value.
     * </pre>
     *
     * <code>optional string language_code = 2;</code>
     */
    public Builder setLanguageCode(
        java.lang.String value) {
      copyOnWrite();
      instance.setLanguageCode(value);
      return this;
    }
    /**
     * <pre>
     * *Required* Language of the request in
     * [IETF BCP 47 syntax](https://tools.ietf.org/html/bcp47). For example:
     * "en-US". If you have selected a language for this `device_id` using the
     * [Settings](https://developers.google.com/assistant/sdk/guides/assistant-settings)
     * menu in your phone's Google Assistant app, that selection will override
     * this value.
     * </pre>
     *
     * <code>optional string language_code = 2;</code>
     */
    public Builder clearLanguageCode() {
      copyOnWrite();
      instance.clearLanguageCode();
      return this;
    }
    /**
     * <pre>
     * *Required* Language of the request in
     * [IETF BCP 47 syntax](https://tools.ietf.org/html/bcp47). For example:
     * "en-US". If you have selected a language for this `device_id` using the
     * [Settings](https://developers.google.com/assistant/sdk/guides/assistant-settings)
     * menu in your phone's Google Assistant app, that selection will override
     * this value.
     * </pre>
     *
     * <code>optional string language_code = 2;</code>
     */
    public Builder setLanguageCodeBytes(
        com.google.protobuf.ByteString value) {
      copyOnWrite();
      instance.setLanguageCodeBytes(value);
      return this;
    }

    /**
     * <pre>
     * *Optional* Location of the device where the query originated.
     * </pre>
     *
     * <code>optional .google.assistant.embedded.v1alpha2.DeviceLocation device_location = 5;</code>
     */
    public boolean hasDeviceLocation() {
      return instance.hasDeviceLocation();
    }
    /**
     * <pre>
     * *Optional* Location of the device where the query originated.
     * </pre>
     *
     * <code>optional .google.assistant.embedded.v1alpha2.DeviceLocation device_location = 5;</code>
     */
    public com.google.assistant.embedded.v1alpha2.DeviceLocation getDeviceLocation() {
      return instance.getDeviceLocation();
    }
    /**
     * <pre>
     * *Optional* Location of the device where the query originated.
     * </pre>
     *
     * <code>optional .google.assistant.embedded.v1alpha2.DeviceLocation device_location = 5;</code>
     */
    public Builder setDeviceLocation(com.google.assistant.embedded.v1alpha2.DeviceLocation value) {
      copyOnWrite();
      instance.setDeviceLocation(value);
      return this;
      }
    /**
     * <pre>
     * *Optional* Location of the device where the query originated.
     * </pre>
     *
     * <code>optional .google.assistant.embedded.v1alpha2.DeviceLocation device_location = 5;</code>
     */
    public Builder setDeviceLocation(
        com.google.assistant.embedded.v1alpha2.DeviceLocation.Builder builderForValue) {
      copyOnWrite();
      instance.setDeviceLocation(builderForValue);
      return this;
    }
    /**
     * <pre>
     * *Optional* Location of the device where the query originated.
     * </pre>
     *
     * <code>optional .google.assistant.embedded.v1alpha2.DeviceLocation device_location = 5;</code>
     */
    public Builder mergeDeviceLocation(com.google.assistant.embedded.v1alpha2.DeviceLocation value) {
      copyOnWrite();
      instance.mergeDeviceLocation(value);
      return this;
    }
    /**
     * <pre>
     * *Optional* Location of the device where the query originated.
     * </pre>
     *
     * <code>optional .google.assistant.embedded.v1alpha2.DeviceLocation device_location = 5;</code>
     */
    public Builder clearDeviceLocation() {  copyOnWrite();
      instance.clearDeviceLocation();
      return this;
    }

    /**
     * <pre>
     * *Optional* If true, the server will treat the request as a new conversation
     * and not use state from the prior request. Set this field to true when the
     * conversation should be restarted, such as after a device reboot, or after a
     * significant lapse of time since the prior query.
     * </pre>
     *
     * <code>optional bool is_new_conversation = 7;</code>
     */
    public boolean getIsNewConversation() {
      return instance.getIsNewConversation();
    }
    /**
     * <pre>
     * *Optional* If true, the server will treat the request as a new conversation
     * and not use state from the prior request. Set this field to true when the
     * conversation should be restarted, such as after a device reboot, or after a
     * significant lapse of time since the prior query.
     * </pre>
     *
     * <code>optional bool is_new_conversation = 7;</code>
     */
    public Builder setIsNewConversation(boolean value) {
      copyOnWrite();
      instance.setIsNewConversation(value);
      return this;
    }
    /**
     * <pre>
     * *Optional* If true, the server will treat the request as a new conversation
     * and not use state from the prior request. Set this field to true when the
     * conversation should be restarted, such as after a device reboot, or after a
     * significant lapse of time since the prior query.
     * </pre>
     *
     * <code>optional bool is_new_conversation = 7;</code>
     */
    public Builder clearIsNewConversation() {
      copyOnWrite();
      instance.clearIsNewConversation();
      return this;
    }

    // @@protoc_insertion_point(builder_scope:google.assistant.embedded.v1alpha2.DialogStateIn)
  }
  protected final Object dynamicMethod(
      com.google.protobuf.GeneratedMessageLite.MethodToInvoke method,
      Object arg0, Object arg1) {
    switch (method) {
      case NEW_MUTABLE_INSTANCE: {
        return new com.google.assistant.embedded.v1alpha2.DialogStateIn();
      }
      case IS_INITIALIZED: {
        return DEFAULT_INSTANCE;
      }
      case MAKE_IMMUTABLE: {
        return null;
      }
      case NEW_BUILDER: {
        return new Builder();
      }
      case VISIT: {
        Visitor visitor = (Visitor) arg0;
        com.google.assistant.embedded.v1alpha2.DialogStateIn other = (com.google.assistant.embedded.v1alpha2.DialogStateIn) arg1;
        conversationState_ = visitor.visitByteString(conversationState_ != com.google.protobuf.ByteString.EMPTY, conversationState_,
            other.conversationState_ != com.google.protobuf.ByteString.EMPTY, other.conversationState_);
        languageCode_ = visitor.visitString(!languageCode_.isEmpty(), languageCode_,
            !other.languageCode_.isEmpty(), other.languageCode_);
        deviceLocation_ = visitor.visitMessage(deviceLocation_, other.deviceLocation_);
        isNewConversation_ = visitor.visitBoolean(isNewConversation_ != false, isNewConversation_,
            other.isNewConversation_ != false, other.isNewConversation_);
        if (visitor == com.google.protobuf.GeneratedMessageLite.MergeFromVisitor
            .INSTANCE) {
        }
        return this;
      }
      case MERGE_FROM_STREAM: {
        com.google.protobuf.CodedInputStream input =
            (com.google.protobuf.CodedInputStream) arg0;
        com.google.protobuf.ExtensionRegistryLite extensionRegistry =
            (com.google.protobuf.ExtensionRegistryLite) arg1;
        try {
          boolean done = false;
          while (!done) {
            int tag = input.readTag();
            switch (tag) {
              case 0:
                done = true;
                break;
              default: {
                if (!input.skipField(tag)) {
                  done = true;
                }
                break;
              }
              case 10: {

                conversationState_ = input.readBytes();
                break;
              }
              case 18: {
                String s = input.readStringRequireUtf8();

                languageCode_ = s;
                break;
              }
              case 42: {
                com.google.assistant.embedded.v1alpha2.DeviceLocation.Builder subBuilder = null;
                if (deviceLocation_ != null) {
                  subBuilder = deviceLocation_.toBuilder();
                }
                deviceLocation_ = input.readMessage(com.google.assistant.embedded.v1alpha2.DeviceLocation.parser(), extensionRegistry);
                if (subBuilder != null) {
                  subBuilder.mergeFrom(deviceLocation_);
                  deviceLocation_ = subBuilder.buildPartial();
                }

                break;
              }
              case 56: {

                isNewConversation_ = input.readBool();
                break;
              }
            }
          }
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          throw new RuntimeException(e.setUnfinishedMessage(this));
        } catch (java.io.IOException e) {
          throw new RuntimeException(
              new com.google.protobuf.InvalidProtocolBufferException(
                  e.getMessage()).setUnfinishedMessage(this));
        } finally {
        }
      }
      case GET_DEFAULT_INSTANCE: {
        return DEFAULT_INSTANCE;
      }
      case GET_PARSER: {
        if (PARSER == null) {    synchronized (com.google.assistant.embedded.v1alpha2.DialogStateIn.class) {
            if (PARSER == null) {
              PARSER = new DefaultInstanceBasedParser(DEFAULT_INSTANCE);
            }
          }
        }
        return PARSER;
      }
    }
    throw new UnsupportedOperationException();
  }


  // @@protoc_insertion_point(class_scope:google.assistant.embedded.v1alpha2.DialogStateIn)
  private static final com.google.assistant.embedded.v1alpha2.DialogStateIn DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new DialogStateIn();
    DEFAULT_INSTANCE.makeImmutable();
  }

  public static com.google.assistant.embedded.v1alpha2.DialogStateIn getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static volatile com.google.protobuf.Parser<DialogStateIn> PARSER;

  public static com.google.protobuf.Parser<DialogStateIn> parser() {
    return DEFAULT_INSTANCE.getParserForType();
  }
}

