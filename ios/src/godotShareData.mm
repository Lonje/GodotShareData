#include "godotShare.h"


#import "app_delegate.h"


GodotShareData::GodotShareData() {
    ERR_FAIL_COND(instance != NULL);
    instance = this;
}

GodotShareData::~GodotShareData() {
    instance = NULL;
}



void GodotShareData::shareText(const String &title, const String &subject, const String &text) {
    
    UIViewController *root_controller = [[UIApplication sharedApplication] delegate].window.rootViewController;
    
    NSString * message = [NSString stringWithCString:text.utf8().get_data() encoding:NSUTF8StringEncoding];
    
    NSArray * shareItems = @[message];
    
    UIActivityViewController * avc = [[UIActivityViewController alloc] initWithActivityItems:shareItems applicationActivities:nil];
    //if iPhone
    if (UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPhone) {
        [root_controller presentViewController:avc animated:YES completion:nil];
    }
    //if iPad
    else {
        // Change Rect to position Popover
        avc.modalPresentationStyle = UIModalPresentationPopover;
        avc.popoverPresentationController.sourceView = root_controller.view;
        avc.popoverPresentationController.sourceRect = CGRectMake(CGRectGetMidX(root_controller.view.bounds), CGRectGetMidY(root_controller.view.bounds),0,0);
        avc.popoverPresentationController.permittedArrowDirections = UIPopoverArrowDirection(0);
        [root_controller presentViewController:avc animated:YES completion:nil];
    }
}

void GodotShareData::sharePic(const String &path, const String &title, const String &subject, const String &text) {
    UIViewController *root_controller = [[UIApplication sharedApplication] delegate].window.rootViewController;
    
    NSString * message = [NSString stringWithCString:text.utf8().get_data() encoding:NSUTF8StringEncoding];
    NSString * imagePath = [NSString stringWithCString:path.utf8().get_data() encoding:NSUTF8StringEncoding];
    
    UIImage *image = [UIImage imageWithContentsOfFile:imagePath];
    
    NSArray * shareItems = @[message, image];
    
    UIActivityViewController * avc = [[UIActivityViewController alloc] initWithActivityItems:shareItems applicationActivities:nil];
     //if iPhone
    if (UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPhone) {
        [root_controller presentViewController:avc animated:YES completion:nil];
    }
    //if iPad
    else {
        // Change Rect to position Popover
        avc.modalPresentationStyle = UIModalPresentationPopover;
        avc.popoverPresentationController.sourceView = root_controller.view;
        avc.popoverPresentationController.sourceRect = CGRectMake(CGRectGetMidX(root_controller.view.bounds), CGRectGetMidY(root_controller.view.bounds),0,0);
        avc.popoverPresentationController.permittedArrowDirections = UIPopoverArrowDirection(0);
        [root_controller presentViewController:avc animated:YES completion:nil];
    }
}



void GodotShareData::_bind_methods() {
    ClassDB::bind_method(D_METHOD("shareText"), &GodotShareData::shareText);
    ClassDB::bind_method(D_METHOD("sharePic"), &GodotShareData::sharePic);
}
