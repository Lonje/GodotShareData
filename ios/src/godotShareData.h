#ifndef GODOT_SHARE_DATA_H
#define GODOT_SHARE_DATA_H

#include <version_generated.gen.h>
#include "reference.h"

class GodotShareData : public Reference {
    GDCLASS(GodotShareData, Reference);
    

protected:
    static void _bind_methods();
    
    GodotShareData* instance;
    
public:

    void shareText(const String &title, const String &subject, const String &text);
    void sharePic(const String &path, const String &title, const String &subject, const String &text);

    GodotShareData();
    ~GodotShareData();
};

#endif
