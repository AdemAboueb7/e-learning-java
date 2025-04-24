package tn.elearning.components;

import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;

public class ToggleSwitch extends Control {
    private boolean switchedOn;
    private EventHandler<Event> onToggleChanged;

    public ToggleSwitch(boolean initialValue) {
        this.switchedOn = initialValue;
        getStyleClass().add("toggle-switch");
    }

    public void setOnToggleChanged(EventHandler<Event> handler) {
        this.onToggleChanged = handler;
    }

    public void fireToggleEvent() {
        if (onToggleChanged != null) {
            onToggleChanged.handle(new Event(EventType.ROOT));
        }
    }

    public boolean isSwitchedOn() {
        return switchedOn;
    }

    public void setSwitchedOn(boolean switchedOn) {
        this.switchedOn = switchedOn;
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new ToggleSwitchSkin(this);
    }

    private static class ToggleSwitchSkin extends javafx.scene.control.SkinBase<ToggleSwitch> {
        private final TranslateTransition translateAnimation;

        public ToggleSwitchSkin(ToggleSwitch toggleSwitch) {
            super(toggleSwitch);

            Rectangle background = new Rectangle(50, 25);
            background.setArcWidth(25);
            background.setArcHeight(25);
            background.getStyleClass().add("background");

            Circle thumb = new Circle(12.5);
            thumb.getStyleClass().add("thumb");

            translateAnimation = new TranslateTransition(Duration.millis(200), thumb);

            StackPane container = new StackPane();
            container.getChildren().addAll(background, thumb);
            container.getStyleClass().add("container");

            updateUI(toggleSwitch.isSwitchedOn());

            container.setOnMouseClicked(e -> {
                toggleSwitch.setSwitchedOn(!toggleSwitch.isSwitchedOn());
                updateUI(toggleSwitch.isSwitchedOn());
                toggleSwitch.fireToggleEvent();
            });

            getChildren().add(container);
        }

        private void updateUI(boolean switchedOn) {
            if (switchedOn) {
                translateAnimation.setToX(25);
                translateAnimation.play();
            } else {
                translateAnimation.setToX(0);
                translateAnimation.play();
            }
        }
    }
}