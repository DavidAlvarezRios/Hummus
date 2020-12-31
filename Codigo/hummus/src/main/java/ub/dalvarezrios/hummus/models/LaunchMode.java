package ub.dalvarezrios.hummus.models;

/**
 * Supported startup modes
 */
public enum LaunchMode {
    /**
     * VirtualBox Qt GUI front-end
     */
    gui,
    /**
     * VBoxHeadless (VRDE Server) front-end
     */
    headless,
    /**
     * VirtualBox SDL front-end
     */
    sdl
}
