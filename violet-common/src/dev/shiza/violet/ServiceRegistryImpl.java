package dev.shiza.violet;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

final class ServiceRegistryImpl implements ServiceRegistry {

  private final Map<Class<?>, ServiceDefinition> services;

  ServiceRegistryImpl() {
    this.services = new HashMap<>();
  }

  @Override
  public void register(final Object service, final ServicePriority priority) {
    register(getRegistrationType(service), service, priority);
  }

  @Override
  public void register(
      final Class<?> serviceType, final Object service, final ServicePriority priority) {
    if (services.containsKey(serviceType)
        && services.get(serviceType).isLessSignificant(priority)) {
      services.remove(serviceType);
    }

    services.computeIfAbsent(
        serviceType, ignored -> new ServiceDefinition(serviceType, service, priority));
  }

  @Override
  public <T> T resolve(final Class<T> serviceType) {
    return Optional.ofNullable(services.get(serviceType))
        .map(ServiceDefinition::service)
        .map(serviceType::cast)
        .orElseThrow(
            () ->
                new ServiceResolvingException(
                    "Could not resolve service with type %s, because of missing service definition."
                        .formatted(serviceType)));
  }

  private Class<?> getRegistrationType(final Object service) {
    final Class<?> serviceType = service.getClass();
    final Class<?>[] interfaces = serviceType.getInterfaces();
    if (interfaces.length > 0) {
      return interfaces[0];
    }
    return serviceType;
  }
}
