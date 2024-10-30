package dev.shiza.violet;

public interface ServiceRegistry {

  void register(final Object service, final ServicePriority priority);

  void register(final Class<?> serviceType, final Object service, final ServicePriority priority);

  default void register(final Object service) {
    register(service, ServicePriority.NORMAL);
  }

  default void register(final Class<?> serviceType, final Object service) {
    register(serviceType, service, ServicePriority.NORMAL);
  }

  <T> T resolve(final Class<T> serviceType);
}
