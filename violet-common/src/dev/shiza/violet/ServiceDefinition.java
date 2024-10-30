package dev.shiza.violet;

record ServiceDefinition(Class<?> serviceType, Object service, ServicePriority priority) {

  public boolean isLessSignificant(final ServicePriority comparison) {
    return priority.ordinal() < comparison.ordinal();
  }
}
