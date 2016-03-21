package io.vertx.ext.discovery;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.util.Objects;

/**
 * Describes a `service`. The record is the only piece of information shared between consumer and provider. It should
 * contains enough metadata to let consumer find the service they want.
 *
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
@DataObject(generateConverter = true)
public class Record {

  public static final String ENDPOINT = "endpoint";

  private JsonObject location;

  private JsonObject metadata = new JsonObject();

  private String name;

  private Status status = Status.UNKNOWN;

  private String registration;

  private String type;

  /**
   * Creates a new {@link Record}.
   */
  public Record() {
    // empty constructor.
  }

  /**
   * Creates a new {@link Record} from its json representation.
   *
   * @param json the json object
   */
  public Record(JsonObject json) {
    RecordConverter.fromJson(json, this);
  }

  /**
   * @return the JSON representation of the current {@link Record}.
   */
  public JsonObject toJson() {
    JsonObject json = new JsonObject();
    RecordConverter.toJson(this, json);
    return json;
  }

  /**
   * Creates a new {@link Record} by copying the values from another {@link Record}.
   *
   * @param other the record to copy
   */
  public Record(Record other) {
    this.location = other.location;
    this.metadata = other.metadata;
    this.name = other.name;
    this.status = other.status;
    this.registration = other.registration;
    this.type = other.type;
  }

  /**
   * @return the json object describing the location of the service. By convention, this json object should contain
   * the {@link #ENDPOINT} entry.
   */
  public JsonObject getLocation() {
    return location;
  }

  /**
   * Sets the json object describing the location of the service. By convention, this json object should contain
   * the {@link #ENDPOINT} entry.
   *
   * @param location the location
   * @return the current {@link Record}
   */
  public Record setLocation(JsonObject location) {
    this.location = location;
    return this;
  }

  /**
   * Gets the metadata attached to the record.
   *
   * @return the metadata, cannot be {@code null}.
   */
  public JsonObject getMetadata() {
    return metadata;
  }

  public Record setMetadata(JsonObject metadata) {
    this.metadata = metadata;
    return this;
  }

  /**
   * Gets the name of the service. It can reflect the service name of the name of the provider.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the service. It can reflect the service name of the name of the provider.
   *
   * @param name the name
   * @return the current {@link Record}
   */
  public Record setName(String name) {
    this.name = name;
    return this;
  }

  /**
   * Gets the current status of the service.
   *
   * @return the status.
   */
  public Status getStatus() {
    return status;
  }

  /**
   * Sets the status of the service. When published, the status is set to{@link Status#UP}. When withdrawn, the
   * status is set to {@link Status#DOWN}.
   *
   * @param status the status, must not be {@code null}
   * @return the current {@link Record}
   */
  public Record setStatus(Status status) {
    Objects.requireNonNull(status);
    this.status = status;
    return this;
  }

  /**
   * Sets the registration id. This method is called when the service is published.
   *
   * @param reg the registration id
   * @return the current {@link Record}
   */
  public Record setRegistration(String reg) {
    this.registration = reg;
    return this;
  }

  /**
   * Gets the registration id if any. Getting a {@code null} result means that the record has not been published.
   *
   * @return the registration id.
   */
  public String getRegistration() {
    return registration;
  }

  /**
   * Gets the service type. The type represents what kind of "resource" is represented by this record. For example it
   * can be "http-endpoint", "database", "message-source"... The set of types is extensible.
   *
   * The type defines how the the service object is retrieved, and also manages the binding. Some records may have no
   * type and let the consumer manage how the service is used.
   *
   * @return the type
   */
  public String getType() {
    return type;
  }

  /**
   * Sets the type of service.
   * @param type the type
   * @return the current {@link Record}
   */
  public Record setType(String type) {
    this.type = type;
    return this;
  }

  /**
   * Checks whether or not the current {@link Record} matches the filter.
   *
   * @param filter the filter
   * @return whether or not the record matches the filter
   */
  public boolean match(JsonObject filter) {
    for (String key : filter.fieldNames()) {
      boolean match;
      switch (key) {
        case "name":
          match = match(getName(), filter.getString("name"));
          break;
        case "registration":
          match = match(getRegistration(), filter.getString("registration"));
          break;
        case "status":
          match = match(getStatus().name(), filter.getString("status"));
          break;
        default:
          // metadata
          match = match(getMetadata().getValue(key), filter.getValue(key));
          break;
      }

      if (!match) {
        return false;
      }
    }

    return true;
  }

  private boolean match(Object actual, Object expected) {
    return actual != null
        && ("*".equals(expected) ||
        (actual instanceof String ?
            ((String) actual).equalsIgnoreCase(expected.toString()) : actual.equals(expected)));
  }

}
