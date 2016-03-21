package io.vertx.ext.discovery;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

/**
 * Discovery bridge allows integrate other discovery technologies with the Vert.x discovery service. It maps entries
 * from another technology to a {@link Record} and maps {@link Record} to a publication in this other technology.
 * Each bridge can decide which services needs to be imported and exported. It can also implement only on way.
 *
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public interface DiscoveryBridge {

  /**
   * Starts the bridge.
   *
   * @param vertx             the vertx instance
   * @param discovery         the discovery service
   * @param configuration     the bridge configuration if any
   * @param completionHandler handler called when the bridge has been initialized
   */
  void start(Vertx vertx, DiscoveryService discovery, JsonObject configuration,
             Handler<AsyncResult<Void>> completionHandler);

  /**
   * Stops the bridge.
   *
   * @param vertx     the vertx instance
   * @param discovery the discovery service
   */
  void stop(Vertx vertx, DiscoveryService discovery);

}
