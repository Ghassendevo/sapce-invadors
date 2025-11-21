module com.game.spaceinvadors {
    requires javafx.controls;
    requires javafx.fxml;

    // Export your application entry
    exports com.game.spaceinvadors.app;

    // Export core (state + manager)
    exports com.game.spaceinvadors.core;
    exports com.game.spaceinvadors.core.state;
    exports com.game.spaceinvadors.core.logger;

    // Export entities
    exports com.game.spaceinvadors.entities;
    exports com.game.spaceinvadors.entities.player;
    exports com.game.spaceinvadors.entities.enemies;
    exports com.game.spaceinvadors.entities.bullets;
    exports com.game.spaceinvadors.entities.powerups;
    exports com.game.spaceinvadors.entities.powerups.decorator;

    // Allow JavaFX reflection for FXML (if needed)
    opens com.game.spaceinvadors.app to javafx.fxml;
}
