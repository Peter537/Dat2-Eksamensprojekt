union()
{
    linear_extrude(height = 195.0, twist = 0.0, scale = 1.0, slices = 1, center = false)
    {
        scale([5000.0, 45.0])
        {
            M52();
        }
    }
    translate([0.0, 67.5, 0.0])
    {
        linear_extrude(height = 195.0, twist = 0.0, scale = 1.0, slices = 1, center = false)
        {
            scale([5000.0, 45.0])
            {
                M52();
            }
        }
    }
}

module M52()
{
    polygon
    (
        points =
        [
            [-0.5, -0.5], 
            [0.5, -0.5], 
            [0.5, 0.5], 
            [-0.5, 0.5]
        ],
        paths =
        [
            [0, 1, 2, 3]
        ]
    );
}
