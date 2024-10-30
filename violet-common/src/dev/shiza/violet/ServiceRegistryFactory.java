package dev.shiza.violet;

public final class ServiceRegistryFactory {

  private ServiceRegistryFactory() {}

  public static ServiceRegistry create() {
    return new ServiceRegistryImpl();
  }
}
