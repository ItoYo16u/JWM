#include <jni.h>
#include "impl/Library.hh"
#include "WindowMac.hh"
#include "WindowDelegate.hh"

@implementation WindowDelegate {
    jwm::WindowMac* fWindow;
}

- (WindowDelegate*)initWithWindow:(jwm::WindowMac*)initWindow {
    fWindow = initWindow;
    return self;
}

- (void)windowDidResize:(NSNotification *)notification {
    NSView* view = fWindow->fNSWindow.contentView;
    CGFloat scale = fWindow->scaleFactor();

    fWindow->fContext->resize();

    jwm::AutoLocal<jobject> eventResize(fWindow->fEnv, jwm::classes::EventResize::make(fWindow->fEnv, view.bounds.size.width * scale, view.bounds.size.height * scale));
    fWindow->onEvent(eventResize.get());

    fWindow->onEvent(jwm::classes::EventPaint::kInstance);
}

- (BOOL)windowShouldClose:(NSWindow*)sender {
    // fWindow->closeWindow();
    jwm::AutoLocal<jobject> event(fWindow->fEnv, jwm::classes::EventClose::make(fWindow->fEnv));
    fWindow->onEvent(event.get());
    return FALSE;
}

@end