require 'vertx/util/utils.rb'
# Generated from io.vertx.ext.discovery.ServiceReference
module VertxServiceDiscovery
  #  Once a consumer has chosen a service, it builds a {::VertxServiceDiscovery::ServiceReference} managing the binding with the chosen
  #  service provider.
  #  <p>
  #  The reference lets the consumer:
  #  * access the service (via a proxy or a client) with the {::VertxServiceDiscovery::ServiceReference#get} method
  #  * release the reference - so the binding between the consumer and the provider is removed
  class ServiceReference
    # @private
    # @param j_del [::VertxServiceDiscovery::ServiceReference] the java delegate
    def initialize(j_del)
      @j_del = j_del
    end
    # @private
    # @return [::VertxServiceDiscovery::ServiceReference] the underlying java delegate
    def j_del
      @j_del
    end
    #  @return the service record.
    # @return [Hash]
    def record
      if !block_given?
        return @j_del.java_method(:record, []).call() != nil ? JSON.parse(@j_del.java_method(:record, []).call().toJson.encode) : nil
      end
      raise ArgumentError, "Invalid arguments when calling record()"
    end
    #  Gets the object to access the service. It can be a proxy, a client or whatever object. The type depends on the
    #  service type and the server itself.
    # @return [Object] the object to access the service
    def get
      if !block_given?
        return ::Vertx::Util::Utils.from_object(@j_del.java_method(:get, []).call())
      end
      raise ArgumentError, "Invalid arguments when calling get()"
    end
    #  Releases the reference. Once released, the consumer must not use the reference anymore.
    # @return [void]
    def release
      if !block_given?
        return @j_del.java_method(:release, []).call()
      end
      raise ArgumentError, "Invalid arguments when calling release()"
    end
  end
end
